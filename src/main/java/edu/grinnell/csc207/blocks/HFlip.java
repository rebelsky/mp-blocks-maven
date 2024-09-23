package edu.grinnell.csc207.blocks;

/**
 * A horizontally flipped ASCII block.
 *
 * @author Samuel A. Rebelsky
 * @author Your Name Here
 */
public class HFlip implements AsciiBlock {
  // +--------+------------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The original block.
   */
  AsciiBlock block;

  // +--------------+------------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Build a new block with the specified contents.
   *
   * @param original
   *   The original block.
   */
  public HFlip(AsciiBlock original) {
    this.block = original;
  } // HFlip(AsciiBlock)

  // +---------+-----------------------------------------------------
  // | Helpers |
  // +---------+

  /**
   * Swap two characters in a character array.
   *
   * @param chars
   *   The array.
   * @param i
   *   The index of one of the characters (0 &lt;= i &lt; arr.length)
   * @param j
   *   The index of the other character (0 &lt;= j &lt; arr.length)
   */
  void swap(char[] chars, int i, int j) {
    char tmp = chars[i];
    chars[i] = chars[j];
    chars[j] = tmp;
  } // swap(char[])

  /**
   * Reverse a string.
   *
   * @param str
   *   The string to reverse.
   *
   * @return
   *   The reversed string.
   */
  String reverse(String str) {
    char[] chars = str.toCharArray();
    int lb = 0;
    int ub = chars.length - 1;
    while (lb < ub) {
      swap(chars, lb++, ub--);
    } // while
    return new String(chars);
  } // reverse(String)

  // +---------+-----------------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Get one row from the block.
   *
   * @param i the number of the row
   *
   * @return row i.
   *
   * @exception Exception
   *   If the row is invalid.
   */
  public String row(int i) throws Exception {
    if ((i < 0) || (i >= this.height())) {
      throw new Exception("Invalid row: " + i);
    } // if
    return reverse(this.block.row(i));
  } // row(int)

  /**
   * Determine how many rows are in the block.
   *
   * @return the number of rows
   */
  public int height() {
    return this.block.height();
  } // height()

  /**
   * Determine how many columns are in the block.
   *
   * @return the number of columns
   */
  public int width() {
    return this.block.width();
  } // width()

  /**
   * Determine if another block is structurally equivalent to this block.
   *
   * @param other
   *   The block to compare to this block.
   *
   * @return true if the two blocks are structurally equivalent and
   *    false otherwise.
   */
  public boolean eqv(AsciiBlock other) {
    return (other instanceof HFlip) && this.eqv((HFlip) other);
  } // eqv(AsciiBlock)

  /**
   * Determine if another hflipped block is structurally equivalent
   * to this block.
   *
   * @param other
   *   The HFlip to compare to this block.
   *
   * @return true if they have equivalent underlying blocks and false
   *   otherwise.
   */
  public boolean eqv(HFlip other) {
    return this.block.eqv(other.block);
  } // eqv(HFlip)
} // class HFlip
