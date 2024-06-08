class PrinterSheetsNonDuplexSuite extends munit.FunSuite {
  def isNonDuplex(pagesAmount: Int, expected: (String, String)): Boolean = {
    val pages = imposition(pagesAmount).iterator
    val (front, back) = nonDuplex(pages)
    val (expectedFront, expectedBack) = expected

    front.mkString(" ") == expectedFront && back.mkString(" ") == expectedBack
  }

  test("4 pages layout") {
    val expected = ("4 1", "2 3")
    assert(isNonDuplex(4, expected))
  }

  test("8 pages layout") {
    val expected = ("8 1 6 3", "2 7 4 5")
    assert(isNonDuplex(8, expected))
  }

  test("15 pages layout") {
    val expected = ("15 1 13 3 11 5 9 7", "2 14 4 12 6 10 8")
    assert(isNonDuplex(15, expected))
  }

  test("17 pages layout") {
    val expected = ("17 1 15 3 13 5 11 7 9", "2 16 4 14 6 12 8 10")
    assert(isNonDuplex(17, expected))
  }
}
