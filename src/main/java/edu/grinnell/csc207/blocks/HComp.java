package edu.grinnell.csc207.blocks;

import java.util.Arrays;

/**
 * The horizontal composition of blocks.
 *
 * @author Samuel A. Rebelsky
 * @author Your Name Here
 */
public class HComp implements AsciiBlock {
  // +--------+------------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The blocks.
   */
  AsciiBlock[] blocks;

  /**
   * How the blocks are aligned.
   */
  VAlignment align;

  // +--------------+------------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Build a horizontal composition of two blocks.
   *
   * @param alignment
   *   The way in which the blocks should be aligned.
   * @param leftBlock
   *   The block on the left.
   * @param rightBlock
   *   The block on the right.
   */
  public HComp(VAlignment alignment, AsciiBlock leftBlock,
      AsciiBlock rightBlock) {
    this.align = alignment;
    this.blocks = new AsciiBlock[] {leftBlock, rightBlock};
  } // HComp(VAlignment, AsciiBlock, AsciiBlock)

  /**
   * Build a horizontal composition of multiple blocks.
   *
   * @param alignment
   *   The alignment of the blocks.
   * @param blocksToCompose
   *   The blocks we will be composing.
   */
  public HComp(VAlignment alignment, AsciiBlock[] blocksToCompose) {
    this.align = alignment;
    this.blocks = Arrays.copyOf(blocksToCompose, blocksToCompose.length);
  } // HComp(Alignment, AsciiBLOCK[])

  // +---------+-----------------------------------------------------
  // | Helpers |
  // +---------+

  /**
   * Compute part of a row from one block.
   *
   * @param block
   *   The block to look at.
   * @param i
   *   The row in the composition.
   * @param height
   *   The height of the composition.
   *
   * @return the appropriate row of block or an appropriate width
   *   set of spaces.
   */
  public String subrow(AsciiBlock block, int i, int height) {
    int blockHeight = block.height();
    int offset = 0;
    if (VAlignment.CENTER == this.align) {
      offset = (height - blockHeight) / 2;
    } else if (VAlignment.BOTTOM == this.align) {
      offset = height - blockHeight;
    } // if/else
    int newi = i - offset;
    if ((newi < 0) || (newi >= blockHeight)) {
      return " ".repeat(block.width());
    } // if
    try {
      return block.row(newi);
    } catch (Exception e) {
      return "!". repeat(block.width());
    } // try/catch
  } // subrow()

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
   *   if i is outside the range of valid rows.
   */
  public String row(int i) throws Exception {
    int height = this.height();
    StringBuilder result = new StringBuilder();
    if ((i < 0) || (i >= height)) {
      throw new Exception("Invalid row: " + i);
    } // if
    for (AsciiBlock block : blocks) {
      result.append(subrow(block, i, height));
    } // for
    return result.toString();
  } // row(int)

  /**
   * Determine how many rows are in the block.
   *
   * @return the number of rows
   */
  public int height() {
    int height = 0;
    for (AsciiBlock block : blocks) {
      height = Math.max(height, block.height());
    } // for
    return height;
  } // height()

  /**
   * Determine how many columns are in the block.
   *
   * @return the number of columns
   */
  public int width() {
    int width = 0;
    for (AsciiBlock block : blocks) {
      width += block.width();
    } // for
    return width;
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
    return (other instanceof HComp) && this.eqv((HComp) other);
  } // eqv(AsciiBlock)

  /**
   * Determine if another horizontal composition is structurally
   * equivalent tot his horizontal composition.
   *
   * @param other
   *   The HComp to compare to this block
   *
   * @return true if the other HComp has the same alignment, the same
   *   number of composed blocks, and equivalent composed blocks
   */
  public boolean eqv(HComp other) {
    // Compare alignments of composed blocks.
    if (this.align != other.align) {
      return false;
    } // if
    // Compare numbers of composed blocks.
    if (this.blocks.length != other.blocks.length) {
      return false;
    } // if
    // Compare individual blocks
    for (int i = 0; i < this.blocks.length; i++) {
      if (! AsciiBlock.eqv(this.blocks[i], other.blocks[i])) {
        return false;
      } // if
    } // for
    // All the comparisons succeeded. We're done!
    return true;
  } // eqv(HComp)

} // class HComp
