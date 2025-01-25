import picocli.CommandLine.Command
import picocli.CommandLine.Parameters
import picocli.CommandLine.Option
import picocli.CommandLine
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.Loader
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.util.logging.Logger
import java.util.logging.Level
import java.util.concurrent.Callable
import scala.util.Try
import scala.util.Failure
import scala.util.Success

@main def Main(args: String*): Int = {
  Logger.getLogger("org.apache.pdfbox").setLevel(Level.OFF);
  CommandLine(PBook()).execute(args*)
}

@Command(
  name = "pbook",
  mixinStandardHelpOptions = true,
  version = Array("pbook 0.4")
)
class PBook() extends Callable[Int] {
  @Parameters(paramLabel = "file", index = "0")
  private var _file: File = null

  @Option(
    names = Array("--duplex"),
    description = Array(
      "Reorder pages into a single pdf file for double-sided printers"
    ),
    paramLabel = "isDuplex"
  )
  private var _isDuplex: Boolean = false

  override def call(): Int = {
    val origDoc = Try(Loader.loadPDF(_file)) match {
      case _ if !_file.exists() =>
        println(s"${_file.getName()} does not exist")
        return 1
      case Failure(ioe) =>
        println(s"${_file.getName()} is not a PDF file")
        return 1
      case Success(pdfDoc) => pdfDoc
    }

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
