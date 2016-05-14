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

package Games.Nim.Players;

import Games.Nim.Move;

/**
 * Allows a human player to play in a terminal.
 * 
 * @author Fabian Pijcke
 */
public class HumanConsole extends Player {
	
	@Override
	public Move pickMove(String avatar) {
		System.out.println(avatar + ", move token of how many places? ");
		String s = System.console().readLine();
		return new Move(Integer.parseInt(s));
	}

}
