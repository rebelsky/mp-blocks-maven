package edu.grinnell.csc207.blocks;

import java.util.Arrays;

/**
 * The vertical composition of blocks.
 *
 * @author Samuel A. Rebelsky
 * @author Your Name Here
 * @author Your Name Here
 */
public class VComp implements AsciiBlock {
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
  HAlignment align;

  // +--------------+------------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Build a vertical composition of two blocks.
   *
   * @param alignment
   *   The way in which the blocks should be aligned.
   * @param topBlock
   *   The block on the top.
   * @param bottomBlock
   *   The block on the bottom.
   */
  public VComp(HAlignment alignment, AsciiBlock topBlock,
      AsciiBlock bottomBlock) {
    this.align = alignment;
    this.blocks = new AsciiBlock[] {topBlock, bottomBlock};
  } // VComp(HAlignment, AsciiBlock, AsciiBlock)

  /**
   * Build a vertical composition of multiple blocks.
   *
   * @param alignment
   *   The alignment of the blocks.
   * @param blocksToCompose
   *   The blocks we will be composing.
   */
  public VComp(HAlignment alignment, AsciiBlock[] blocksToCompose) {
    this.align = alignment;
    this.blocks = Arrays.copyOf(blocksToCompose, blocksToCompose.length);
  } // VComp(HAlignment, AsciiBLOCK[])

  // +---------+-----------------------------------------------------
  // | Helpers |
  // +---------+

  /**
   * Align one row.
   *
   * @param str
   *   A string.
   */
  public String alignRow(String str) {
    String result = "";
    int diff = this.width() - str.length();
    if (HAlignment.LEFT == this.align) {
      result = str + " ".repeat(diff);
    } else if (HAlignment.CENTER == this.align) {
      result = " ".repeat(diff / 2) + str + " ".repeat(diff - diff / 2);
    } else {
      result = " ".repeat(diff) + str;
    } // if/else
    return result;
  } // alignRow(String)

  // +---------+-----------------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Get one row from the block.
   *
   * @param i the number of the row
   *
   * @return the contents row i.
   *
   * @exception Exception
   *   if i is outside the range of valid rows.
   */
  public String row(int i) throws Exception {
    if ((i < 0) || (i >= this.height())) {
      throw new Exception("Invalid row: " + i);
    } // if
    for (AsciiBlock block : blocks) {
      int blockHeight = block.height();
      if (i < blockHeight) {
        return alignRow(block.row(i));
      } // if
      i = i - blockHeight;
    } // for
    throw new Exception("Ran out of blocks.");
  } // row(int)

  /**
   * Determine how many rows are in the block.
   *
   * @return the number of rows
   */
  public int height() {
    int height = 0;
    for (AsciiBlock block : blocks) {
      height = height + block.height();
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
      width = Math.max(width, block.width());
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
    return (other instanceof VComp) && this.eqv((VComp) other);
  } // eqv(AsciiBlock)

  /**
   * Determine if another vertical composition is structurally equivalent
   * to this vertical composition.
   *
   * @param other
   *   The VComp to compare to this VComp
   *
   * @return true if the two blocks have the same alignment and the same
   *   sets of subblocks.
   */
  public boolean eqv(VComp other) {
    // Make sure that we have the same alignment.
    if (this.align != other.align) {
      return false;
    } // if
    // Make sure that we have the same number of blocks.
    if (this.blocks.length != other.blocks.length) {
      return false;
    } // if
    // Make sure that all the subblocks are equivalent.
    for (int i = 0; i < this.blocks.length; i++) {
      if (!AsciiBlock.eqv(this.blocks[i], other.blocks[i])) {
        return false;
      } // if
    } // for
    return true;
  } // eqv(VComp)
} // class VComp
