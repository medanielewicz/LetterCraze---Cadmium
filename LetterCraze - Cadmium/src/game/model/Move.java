package game.model;

public class Move {
	
	Word word;
	Level level;
	Board board;
	
	Move (Word theWord, Level theLevel) {
		this.word = theWord;
		this.level = theLevel;
		this.board = this.level.board;
	}
	
	public boolean doMove () {
		if (this.isValid()) {
			for (int i = 0; i < this.word.getSelectedSquares().size(); i++) {
				this.word.getSelectedSquares().get(i).removeTile();
			}
				this.level.addWord(this.word);
				this.level.checkStarProgress();
				this.level.board.floatTilesUp();
				this.level.repopulate(this.level.board);
				if (this.level.getLevelType().equalsIgnoreCase("puzzle")) {
					((PuzzleLevel) this.level).didMove();
				}
				return true;
		}
		
		else {
			return false;
		}
	}
	
	public boolean undoMove () {
		if (this.level.getLevelType().equalsIgnoreCase("puzzle")) {
			((PuzzleLevel) this.level).undidMove();
		}
		this.level.removeWord();
		this.level.checkStarProgress();
		this.level.setBoard(this.board);
		return true;
	} 
	
	public boolean isValid () {
		if (this.board.isValidSelection()) {
			if (this.level.getLevelType().equalsIgnoreCase("theme")) {
				return ((ThemeLevel)this.level).themeWords.containsWord(this.word.getActualString());
			}
			if (this.word.isValidWord(this.level.getDictionary())) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	public int numOfSTiles (Square sSquares []) {
		int count = 0;
		for (int i = 0; i < 36; i++) {
			if (sSquares [i] != null) {
				count++;
			}
		}
			return count;
	}

}
