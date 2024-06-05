@main def main(pagesAmount: Int) =
  val booklet = imposition(pagesAmount)
  val (firstPass, secondPass) = booklet.partitionMap(or => or)

  println(s"first pass: ${firstPass.flatten.mkString(", ")}")
  println(s"second pass: ${secondPass.flatten.mkString(", ")}")

def imposition(pagesAmount: Int) =
  // [1, 2, 3, 4, 5, 6, 7, 8]           =>
  // [(8, 1), (2, 7), (6, 3), (4, 5)]   =>
  // [(8, 1), (6, 3)], [(2, 7), (4, 5)]
  for i <- 1 to (pagesAmount / 2.0d).ceil.toInt
  yield
    val j = pagesAmount - i + 1

    i.compare(j) match
      case 0 if (pagesAmount - 1) % 4 == 0 => Left(List(i))
      case 0                               => Right(List(i))
      case _ if i % 2 == 0                 => Right(List(i, j))
      case _                               => Left(List(j, i))
