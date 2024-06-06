class PerfectBinding extends munit.FunSuite {
  test("4 pages layout") {
    val pages = imposition(4).iterator
    val expected = "4 1 2 3"

    assertEquals(pages.mkString(" "), expected)
  }

  test("8 pages layout") {
    val pages = imposition(8).iterator
    val expected = "8 1 2 7 6 3 4 5"

    assertEquals(pages.mkString(" "), expected)
  }

  test("17 pages layout") {
    val pages = imposition(17).iterator
    val expected = "17 1 2 16 15 3 4 14 13 5 6 12 11 7 8 10 9"

    assertEquals(pages.mkString(" "), expected)
  }
}
