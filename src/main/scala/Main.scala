def tip = "Tip: "
def usage = "Usage: pbook number-of-pages [--duplex] [--no-tip] [--help]"
def printAndExit(stuff: Any, exitCode: Int) =
  println(stuff); sys.exit(exitCode)

//TODO tips as optional cmdline arguments
// - page flip ascii images
@main def main(args: String*): Unit = {
  if args.isEmpty || args.head == "--help" then printAndExit(usage, 0)

  val pagesAmount = args.head.toIntOption.getOrElse {
    printAndExit(s"\"${args.head}\" is not a valid amount of pages", 1)
  }
  val booklet = imposition(pagesAmount)

  var showTip = true
  var duplexAvailable = false

  args.drop(1).foreach {
    case "--duplex" => duplexAvailable = true
    case "--no-tip" => showTip = false
    case _          => printAndExit(usage, 1)
  }

  if duplexAvailable then {
    println(booklet.mkString(","))
  } else {
    val (front, back) = nonDuplex(booklet)
    println(front.mkString(","))
    println(back.mkString(","))
  }

  if showTip then println(tip)
}

def imposition(pagesAmount: Int): Iterator[Int] = {
  // [1, 2, 3, 4, 5, 6, 7, 8]           =>
  // [(8, 1), (2, 7), (6, 3), (4, 5)]   =>
  // [(8, 1), (6, 3)], [(2, 7), (4, 5)]
  def sheets(p1: Int, p2: Int): LazyList[Int] =
    (p1 - p2) match
      case 1                => LazyList.empty
      case 0                => p1 #:: LazyList.empty
      case _ if p1 % 2 == 1 => p2 #:: p1 #:: sheets(p1 + 1, p2 - 1)
      case _                => p1 #:: p2 #:: sheets(p1 + 1, p2 - 1)
  sheets(1, pagesAmount).iterator
}

def nonDuplex(printerSheets: Iterator[Int]): (Iterator[Int], Iterator[Int]) = {
  val (front, back) = printerSheets.iterator
    .grouped(2)
    .zipWithIndex
    .partition(_._2 % 2 == 0)

  (front.map(_._1).flatten, back.map(_._1).flatten)
}
