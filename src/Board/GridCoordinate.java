/*
 Copyright 2015-2016 Fabian Pijcke

 This file is part of MetaBoard.

 MetaBoard is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 MetaBoard is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with MetaBoard. If not, see <http://www.gnu.org/licenses/>.
 */

package Board;

/**
 * An immutable 2D coordinate using integers.
 * 
 * @author Fabian Pijcke
 */
public class GridCoordinate {
    private final int x, y;
    
    /**
     * Constructs a 2D coordinate.
     * 
     * @param x
     * @param y
     */
    public GridCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * @return the x coordinate.
     */
    public int getX() {
        return x;
    }
    
    /**
     * @return the y coordinate.
     */
    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }
    
}
