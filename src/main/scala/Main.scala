//TODO tips as optional cmdline arguments
// - non-duplex printing: print odd first, then even
// - page flip ascii images
@main def main(pagesAmount: Int) =
  val booklet = imposition(pagesAmount)
  println(booklet.iterator.mkString(","))

def imposition(pagesAmount: Int): IterableOnce[Int] = {
  // [1, 2, 3, 4, 5, 6, 7, 8]           =>
  // [(8, 1), (2, 7), (6, 3), (4, 5)]   =>
  // [(8, 1), (6, 3)], [(2, 7), (4, 5)]
  def sheets(p1: Int, p2: Int): LazyList[Int] =
    (p1 - p2) match
      case 1                => LazyList.empty
      case 0                => p1 #:: LazyList.empty
      case _ if p1 % 2 == 1 => p2 #:: p1 #:: sheets(p1 + 1, p2 - 1)
      case _                => p1 #:: p2 #:: sheets(p1 + 1, p2 - 1)
  sheets(1, pagesAmount)
}
