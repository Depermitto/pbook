@main def main(pagesAmount: Int) =
  val booklet = imposition(pagesAmount)
  var (firstPass, secondPass) = twoPasses(booklet)

  println(s"first pass: ${firstPass.flatten.mkString(", ")}")
  println(s"second pass: ${secondPass.flatten.mkString(", ")}")

def imposition(pagesAmount: Int) =
  // [1, 2, 3, 4, 5, 6, 7, 8] => [(8, 1), (2, 7), (6, 3), (4, 5)]
  for i <- 1 to (pagesAmount / 2.0d).ceil.toInt
  yield
    val pair = List(i, pagesAmount - i + 1).distinct
    if i % 2 == 0 then pair else pair.reverse

def twoPasses(printerSpreads: IndexedSeq[List[Int]]) =
  // [(8, 1), (2, 7), (6, 3), (4, 5)] => [(8, 1), (6, 3)]
  //                                     [(2, 7), (4, 5)]
  printerSpreads.partitionMap {
    case List(i, j) if i > j                           => Left(List(i, j))
    case List(i, j)                                    => Right(List(i, j))
    case List(i) if (printerSpreads.size - 1) % 4 == 0 => Left(List(i))
    case List(i)                                       => Right(List(i))
    case _                                             => ???
  }
