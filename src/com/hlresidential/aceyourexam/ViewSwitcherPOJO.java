package com.hlresidential.aceyourexam;

public class ViewSwitcherPOJO {
	
	private boolean screenHasRandomAnswers = false;
	private boolean screenIsAnswered = false;
    private int screenRowID = 0;
    private boolean isOddScreen = false;
    private String origCorrectAnswer = " ";
    private String answerOneText = " ";
    private String answerTwoText = " ";
    private String answerThreeText = " ";
    private String answerFourText = " ";
    private String dbAnswerOne = " ";
    private String dbAnswerTwo = " ";
    private String dbAnswerThree = " ";
    private String dbAnswerFour = " ";
    private String translatedLastAnswer = " ";



	public boolean isScreenHasRandomAnswers() {
		return screenHasRandomAnswers;
	}
	public void setScreenHasRandomAnswers(boolean screenHasRandomAnswers) {
		this.screenHasRandomAnswers = screenHasRandomAnswers;
	}
	public boolean isScreenIsAnswered() {
		return screenIsAnswered;
	}
	public void setScreenIsAnswered(boolean screenIsAnswered) {
		this.screenIsAnswered = screenIsAnswered;
	}
	public int getScreenRowID() {
		return screenRowID;
	}
	public void setScreenRowID(int screenRowID) {
		this.screenRowID = screenRowID;
	}
	public boolean isOddScreen() {
		return isOddScreen;
	}
	public void setOddScreen(boolean isOddScreen) {
		this.isOddScreen = isOddScreen;
	}
	public String getOrigCorrectAnswer() {
		return origCorrectAnswer;
	}
	public void setOrigCorrectAnswer(String origCorrectAnswer) {
		this.origCorrectAnswer = origCorrectAnswer;
	}
	public String getAnswerOneText() {
		return answerOneText;
	}
	public void setAnswerOneText(String answerOneText) {
		this.answerOneText = answerOneText;
	}
	public String getAnswerTwoText() {
		return answerTwoText;
	}
	public void setAnswerTwoText(String answerTwoText) {
		this.answerTwoText = answerTwoText;
	}
	public String getAnswerThreeText() {
		return answerThreeText;
	}
	public void setAnswerThreeText(String answerThreeText) {
		this.answerThreeText = answerThreeText;
	}
	public String getAnswerFourText() {
		return answerFourText;
	}
	public void setAnswerFourText(String answerFourText) {
		this.answerFourText = answerFourText;
	}
	public String getDbAnswerOne() {
		return dbAnswerOne;
	}
	public void setDbAnswerOne(String dbAnswerOne) {
		this.dbAnswerOne = dbAnswerOne;
	}
	public String getDbAnswerTwo() {
		return dbAnswerTwo;
	}
	public void setDbAnswerTwo(String dbAnswerTwo) {
		this.dbAnswerTwo = dbAnswerTwo;
	}
	public String getDbAnswerThree() {
		return dbAnswerThree;
	}
	public void setDbAnswerThree(String dbAnswerThree) {
		this.dbAnswerThree = dbAnswerThree;
	}
	public String getDbAnswerFour() {
		return dbAnswerFour;
	}
	public void setDbAnswerFour(String dbAnswerFour) {
		this.dbAnswerFour = dbAnswerFour;
	}
	public String getTranslatedLastAnswer() {
		return translatedLastAnswer;
	}
	public void setTranslatedLastAnswer(String translatedLastAnswer) {
		this.translatedLastAnswer = translatedLastAnswer;
	}
	
	public int getCorrectAnswer() {
		int correctAnswer = 0;
		if(origCorrectAnswer.equals("1") && dbAnswerOne.equals("1")) { correctAnswer = 1; } else 
		if(origCorrectAnswer.equals("1") && dbAnswerTwo.equals("1")) { correctAnswer = 2; } else
		if(origCorrectAnswer.equals("1") && dbAnswerThree.equals("1")) { correctAnswer = 3; } else
		if(origCorrectAnswer.equals("1") && dbAnswerFour.equals("1")) { correctAnswer = 4; } else
		
		if(origCorrectAnswer.equals("2") && dbAnswerOne.equals("2")) { correctAnswer = 1; } else 
		if(origCorrectAnswer.equals("2") && dbAnswerTwo.equals("2")) { correctAnswer = 2; } else
		if(origCorrectAnswer.equals("2") && dbAnswerThree.equals("2")) { correctAnswer = 3; } else
		if(origCorrectAnswer.equals("2") && dbAnswerFour.equals("2")) { correctAnswer = 4; } else
			
		if(origCorrectAnswer.equals("3") && dbAnswerOne.equals("3")) { correctAnswer = 1; } else 
		if(origCorrectAnswer.equals("3") && dbAnswerTwo.equals("3")) { correctAnswer = 2; } else
		if(origCorrectAnswer.equals("3") && dbAnswerThree.equals("3")) { correctAnswer = 3; } else
		if(origCorrectAnswer.equals("3") && dbAnswerFour.equals("3")) { correctAnswer = 4; } else	
			
			
		if(origCorrectAnswer.equals("4") && dbAnswerOne.equals("4")) { correctAnswer = 1; } else 
		if(origCorrectAnswer.equals("4") && dbAnswerTwo.equals("4")) { correctAnswer = 2; } else
		if(origCorrectAnswer.equals("4") && dbAnswerThree.equals("4")) { correctAnswer = 3; } else
		if(origCorrectAnswer.equals("4") && dbAnswerFour.equals("4")) { correctAnswer = 4; }
		
		return correctAnswer;
	}
	
	public void setAll(boolean screenHasAnswers, boolean screenIsAnswered, int screenRowID, boolean isOddScreen,
			String origCorrectAnswer, String answerOneText, String answerTwoText, String answerThreeText, String answerFourText,  
			String dbAnswerOne, String dbAnswerTwo, String dbAnswerThree, String dbAnswerFour,
			String translatedLastAnswer) {
		this.screenHasRandomAnswers = screenHasAnswers;
		this.screenIsAnswered = screenIsAnswered;
		this.screenRowID = screenRowID;
		this.isOddScreen = isOddScreen;
		this.origCorrectAnswer = origCorrectAnswer;
		this.answerOneText = answerOneText;
		this.answerTwoText = answerTwoText;
		this.answerThreeText = answerThreeText;
		this.answerFourText = answerFourText;
		this.dbAnswerOne = dbAnswerOne;
		this.dbAnswerTwo = dbAnswerTwo;
		this.dbAnswerThree = dbAnswerThree;
		this.dbAnswerFour = dbAnswerFour;
		this.translatedLastAnswer = translatedLastAnswer;
		
	}  

}
