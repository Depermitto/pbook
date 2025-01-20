class PrinterSheetsSimplexSuite extends munit.FunSuite {
  def isSimplex(pagesAmount: Int, expected: (String, String)): Boolean = {
    val pages = Sheets.imposition(pagesAmount).iterator
    val (front, back) = Sheets.simplex(pages)
    val (expectedFront, expectedBack) = expected

    front.mkString(" ") == expectedFront && back.mkString(" ") == expectedBack
  }

  test("4 pages layout") {
    val expected = ("4 1", "2 3")
    assert(isSimplex(4, expected))
  }

  test("8 pages layout") {
    val expected = ("8 1 6 3", "2 7 4 5")
    assert(isSimplex(8, expected))
  }

  test("15 pages layout") {
    val expected = ("15 1 13 3 11 5 9 7", "2 14 4 12 6 10 8")
    assert(isSimplex(15, expected))
  }

  test("17 pages layout") {
    val expected = ("17 1 15 3 13 5 11 7 9", "2 16 4 14 6 12 8 10")
    assert(isSimplex(17, expected))
  }
}
