package base64

object Encode {
  def encodeGroup(alphabet: Alphabet)(xs: Array[Char]): String = {
    val fill: Array[Char] = ("0" * (6 - xs.size)).toArray
    def concat(a: Array[Char], b: Array[Char]): Array[Char] = {
      val res = new Array[Char](a.length + b.length)
      System.arraycopy(a, 0, res, 0, a.length)
      System.arraycopy(b, 0, res, a.length, b.length)
      res
    }
    val value = alphabet.values(toInt(concat(xs, fill)))
    val pad = (Pad * (xs.size % 3))
    value.toChar + pad
  }

  def encodeWith(alphabet: Alphabet)(bytes: Array[Byte]): String =
    bytes.map(toBinaryStr)
         .flatten
         .grouped(6)
         .map(encodeGroup(alphabet))
         .mkString("")

  /** Encodes an array of bytes into a base64 encoded string
   *   <http://www.apps.ietf.org/rfc/rfc4648.html> */
  def apply(bytes: Array[Byte]) =
    encodeWith(StdAlphabet)(bytes)

  /** Encodes an array of bytes into a base64 encoded string
   *  which accounts for url encoding provisions */
  def urlSafe =
    encodeWith(URLSafeAlphabet)_
}
