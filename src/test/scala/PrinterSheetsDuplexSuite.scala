class PrinterSheetsDuplexSuite extends munit.FunSuite {
  def isDuplex(pagesAmount: Int, expected: String): Boolean = {
    val pages = Sheets.imposition(pagesAmount).iterator
    pages.mkString(" ") == expected
  }

  test("4 pages layout") {
    val expected = "4 1 2 3"
    assert(isDuplex(4, expected))
  }

  test("8 pages layout") {
    val expected = "8 1 2 7 6 3 4 5"
    assert(isDuplex(8, expected))
  }

  test("15 pages layout") {
    val expected = "15 1 2 14 13 3 4 12 11 5 6 10 9 7 8"
    assert(isDuplex(15, expected))
  }

  test("17 pages layout") {
    val expected = "17 1 2 16 15 3 4 14 13 5 6 12 11 7 8 10 9"
    assert(isDuplex(17, expected))
  }
}
