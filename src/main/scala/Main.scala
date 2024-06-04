@main def main(pagesAmount: Int): Unit =
  val booklet = imposition(pagesAmount)

  println(booklet.iterator.mkString(", "))

def imposition(pagesAmount: Int): List[Int] =
  // 1 2 3 4 5 6 7 8
  // 1 8 2 7 3 6 4 5
  List.from(
    for
      i <- 1 to (pagesAmount / 2.0d).ceil.toInt
      j <- List(i, pagesAmount - i + 1).distinct
    yield j
  )
