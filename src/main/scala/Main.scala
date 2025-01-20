import java.util.concurrent.Callable
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters
import picocli.CommandLine.Option
import picocli.CommandLine
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.Loader
import java.io.File

@main def Main(args: String*): Int = CommandLine(PBook()).execute(args*)

@Command(
  name = "pbook",
  mixinStandardHelpOptions = true,
  version = Array("pbook 0.3")
)
class PBook() extends Callable[Int] {
  @Parameters(paramLabel = "file", index = "0")
  private var _file: File = null

  @Option(
    names = Array("--duplex"),
    description = Array(
      "Reorder pages into a single pdf file for duplex-equipped printers"
    ),
    paramLabel = "isDuplex"
  )
  private var _isDuplex: Boolean = false

  override def call(): Int = {
    val origDoc = Loader.loadPDF(_file)
    val printerSheets = Sheets.imposition(origDoc.getNumberOfPages)

    if (_isDuplex) {
      val doc = copyDoc(origDoc, printerSheets)
      doc.save(getNameAppend(_file, "booklet"))
    } else {
      val (frontPages, backPages) = Sheets.simplex(printerSheets)

      val frontDoc = copyDoc(origDoc, frontPages)
      frontDoc.save(getNameAppend(_file, "booklet_front"))

      val backDoc = copyDoc(origDoc, backPages)
      backDoc.save(getNameAppend(_file, "booklet_back"))
    }
    return 0
  }
}

def getNameAppend(file: File, appendage: String): String = {
  val filename = file.getName()
  val extIndex = filename.lastIndexOf(".")

  return if (extIndex == -1) {
    filename + "_" + appendage
  } else {
    val filenameNoExt = filename.substring(0, extIndex)
    val extWithDot = filename.substring(extIndex)
    filenameNoExt + "_" + appendage + extWithDot
  }
}

def copyDoc(origDoc: PDDocument, pages: Iterator[Int]): PDDocument = {
  val doc = PDDocument()
  val origPages = origDoc.getPages()
  pages.foreach(pageNo => doc.addPage(origPages.get(pageNo - 1)))
  return doc
}
