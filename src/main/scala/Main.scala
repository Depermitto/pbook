import java.util.concurrent.Callable
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import picocli.CommandLine.Parameters
import java.io.File
import picocli.CommandLine
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.Loader

@Command(
  name = "pbook",
  mixinStandardHelpOptions = true,
  version = Array("pbook 0.2")
)
class PBook() extends Callable[Unit] {
  @Parameters(paramLabel = "filename", index = "0")
  private var _file: File = null

  @Option(
    names = Array("-o", "--output"),
    description = Array("Specify custom path for the output pdf"),
    paramLabel = "filename"
  )
  private var _outputFilename: String = "booklet.pdf"

  override def call() = {
    val origPages = Loader.loadPDF(_file).getPages()
    val printerSheets = imposition(origPages.getCount)

    val doc = PDDocument()
    printerSheets.foreach(pageNo => doc.addPage(origPages.get(pageNo - 1)))

    // safe save
    File(_outputFilename).delete
    doc.save(_outputFilename)
  }
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

@deprecated("no longer necessary as program now reorders pdf files directly")
def simplex(printerSheets: Iterator[Int]): (Iterator[Int], Iterator[Int]) = {
  // [(8, 1), (2, 7), (6, 3), (4, 5)]   =>
  // [(8, 1), (6, 3)], [(2, 7), (4, 5)]
  val (front, back) = printerSheets
    .grouped(2)
    .zipWithIndex
    .partition(_._2 % 2 == 0)

  (front.map(_._1).flatten, back.map(_._1).flatten)
}

@main def Main(args: String*) = CommandLine(PBook()).execute(args*)
