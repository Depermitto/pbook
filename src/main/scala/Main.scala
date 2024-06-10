import java.util.concurrent.Callable
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters
import picocli.CommandLine
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.Loader
import java.nio.file.Path
import java.io.File

@Command(
  name = "pbook",
  mixinStandardHelpOptions = true,
  version = Array("pbook 0.3")
)
class PBook() extends Callable[Int] {
  @Parameters(paramLabel = "filename", index = "0")
  private var _filepath: Path = null

  @picocli.CommandLine.Option(
    names = Array("-d", "--duplex"),
    description = Array(
      "Reorder pages into a single pdf file for duplex-equipped printers"
    ),
    paramLabel = "isDuplex"
  )
  private var _isDuplex: Boolean = false

  @picocli.CommandLine.Option(
    names = Array("-n", "--name-preserve"),
    description = Array(
      "How much of original filename to preserve"
    ),
    paramLabel = "n"
  )
  private var _n: Int = 10

  override def call(): Int = {
    val origDoc = {
      val docFile = _filepath.toFile()
      if !docFile.exists then {
        println(s"\"$_filepath\" is not a valid file")
        return 1
      }

      Loader.loadPDF(docFile)
    }
    val printerSheets = imposition(origDoc.getNumberOfPages)

    if _isDuplex then
      val doc = origDoc.copy(printerSheets)
      doc.save(_filepath.getShortName(_n) + "_booklet.pdf")
    else
      val (frontPages, backPages) = simplex(printerSheets)

      val frontDoc = origDoc.copy(frontPages)
      frontDoc.save(_filepath.getShortName(_n) + "_booklet_front.pdf")

      val backDoc = origDoc.copy(backPages)
      backDoc.save(_filepath.getShortName(_n) + "_booklet_back.pdf")

    0
  }

  extension (srcDoc: PDDocument)
    private def copy(pages: Iterator[Int]): PDDocument =
      val doc = PDDocument()
      pages.foreach(pageNo => doc.addPage(srcDoc.getPages.get(pageNo - 1)))

      doc

  extension (path: Path)
    private def getShortName(n: Int): String =
      path.getName(path.getNameCount - 1).toString.slice(0, n)
}

def imposition(pagesAmount: Int): Iterator[Int] = {
  // [1, 2, 3, 4, 5, 6, 7, 8]           =>
  // [(8, 1), (2, 7), (6, 3), (4, 5)]
  def sheets(p1: Int, p2: Int): LazyList[Int] =
    (p1 - p2) match
      case 1                => LazyList.empty
      case 0                => p1 #:: LazyList.empty
      case _ if p1 % 2 == 1 => p2 #:: p1 #:: sheets(p1 + 1, p2 - 1)
      case _                => p1 #:: p2 #:: sheets(p1 + 1, p2 - 1)
  sheets(1, pagesAmount).iterator
}

def simplex(printerSheets: Iterator[Int]): (Iterator[Int], Iterator[Int]) = {
  // [(8, 1), (2, 7), (6, 3), (4, 5)]   =>
  // [(8, 1), (6, 3)], [(2, 7), (4, 5)]
  val (front, back) = printerSheets
    .grouped(2)
    .zipWithIndex
    .partition(_._2 % 2 == 0)

  (front.map(_._1).flatten, back.map(_._1).flatten)
}

@main def Main(args: String*): Int = CommandLine(PBook()).execute(args*)
