package com.hlresidential.aceyourexam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;

public class RandomizeAnswers {
	private Cursor cursor;
	private Integer iCorrect, iLastAnswer = 0;
	private Integer iCorrectPos = 0;
	static private int no4 = 4;
	private int j = 0;

 String answerString[] = {"0"," "," "," "," ","0","0","0","0","0"};
	
 
	
public RandomizeAnswers() {
		// TODO Auto-generated constructor stub
	}


	public  RandomizeAnswers(Cursor cursor){
		
		  this.cursor = cursor;
}


public String[] getRandomAnswers(Cursor cursor)	{

	iCorrectPos = cursor.getInt(cursor
			.getColumnIndexOrThrow(HLdb.COL_ANSWER_KEY));
	iLastAnswer = cursor.getInt(cursor
			.getColumnIndexOrThrow(HLdb.COL_LAST_ANSWER));

	List<Integer> myArray = new ArrayList<Integer>(no4); 

	for (int i = 0; i < no4; i++)
		myArray.add(i);

	Collections.shuffle(myArray);

	for (int i = 0; i < no4; i++) {
		j = i + 1;
		// Answer A
		if (j == 1) {
			setAnswer(j, myArray.get(i), cursor);
		} else if (j == 2) {
			setAnswer(j, myArray.get(i), cursor);
		} else
		if (j == 3) {
			setAnswer(j, myArray.get(i), cursor);
		} else
		if (j == 4) {
			setAnswer(j, myArray.get(i), cursor);
		}

	}

	if(answerString[0].equals("0")) {} else {
		if(answerString[0].equals("1")) {
			answerString[9] = answerString[5];
		} else if(answerString[0].equals("2")) {
			answerString[9] = answerString[6];
		} else if(answerString[0].equals("3")) {
			answerString[9] = answerString[7];
		} else if(answerString[0].equals("4")) {
			answerString[9] = answerString[8];
		} 
	}
	
	return answerString;
}

private void setAnswer(int j, int i,
		Cursor cursor) {


	if (j == 1) {
		switch (i) {
		case 0:
			answerString[1] = cursor.getString(cursor
							.getColumnIndexOrThrow(HLdb.COL_ANSWER1));
			answerString[5] = "1";
			if (iCorrectPos == 1) iCorrect = j; 
			break;
		case 1:
			answerString[1] = cursor.getString(cursor
							.getColumnIndexOrThrow(HLdb.COL_ANSWER2));
			answerString[5] = "2";
			if (iCorrectPos == 2) iCorrect = j; 
			break;
		case 2:
			answerString[1] = cursor.getString(cursor
							.getColumnIndexOrThrow(HLdb.COL_ANSWER3));
			answerString[5] = "3";
			if (iCorrectPos == 3) iCorrect = j; 
			break;
		case 3:
			answerString[1] = cursor.getString(cursor
							.getColumnIndexOrThrow(HLdb.COL_ANSWER4));
			answerString[5] = "4";
			if (iCorrectPos == 4) iCorrect = j; 
			break;
		}
//		Log.e("RandomizeAnswers","Randomized: 1 is now " + answerString[5]);
	}
	if (j == 2) {
		switch (i) {
		case 0:
			answerString[2] = cursor.getString(cursor
							.getColumnIndexOrThrow(HLdb.COL_ANSWER1));
			answerString[6] = "1";
			if (iCorrectPos == 1) iCorrect = j; 
			break;
		case 1:
			answerString[2] = cursor.getString(cursor
							.getColumnIndexOrThrow(HLdb.COL_ANSWER2));
			answerString[6] = "2";
			if (iCorrectPos == 2) iCorrect = j; 
			break;
		case 2:
			answerString[2] = cursor.getString(cursor
							.getColumnIndexOrThrow(HLdb.COL_ANSWER3));
			answerString[6] = "3";
			if (iCorrectPos == 3) iCorrect = j; 
			break;
		case 3:
			answerString[2] = cursor.getString(cursor
							.getColumnIndexOrThrow(HLdb.COL_ANSWER4));
			answerString[6] = "4";
			if (iCorrectPos == 4) iCorrect = j; 
			break;
		}
//		Log.e("RandomizeAnswers","Randomized: 2 is now " + answerString[6]);
	}
	if (j == 3) {
		switch (i) {
		case 0:
			answerString[3] = cursor.getString(cursor
							.getColumnIndexOrThrow(HLdb.COL_ANSWER1));
			answerString[7] = "1";
			if (iCorrectPos == 1) iCorrect = j; 
			break;
		case 1:
			answerString[3] = cursor.getString(cursor
							.getColumnIndexOrThrow(HLdb.COL_ANSWER2));
			answerString[7] = "2";
			if (iCorrectPos == 2) iCorrect = j; 
			break;
		case 2:
			answerString[3] = cursor.getString(cursor
							.getColumnIndexOrThrow(HLdb.COL_ANSWER3));
			answerString[7] = "3";
			if (iCorrectPos == 3) iCorrect = j; 
			break;
		case 3:
			answerString[3] = cursor.getString(cursor
							.getColumnIndexOrThrow(HLdb.COL_ANSWER4));
			answerString[7] = "4";
			if (iCorrectPos == 4) iCorrect = j; 
			break;
		}
//		Log.e("RandomizeAnswers","Randomized: 3 is now " + answerString[7]);
	}
	if (j == 4) {
		switch (i) {
		case 0:
			answerString[4] = cursor.getString(cursor
							.getColumnIndexOrThrow(HLdb.COL_ANSWER1));
			answerString[8] = "1";
			if (iCorrectPos == 1) iCorrect = j; 
			break;
		case 1:
			answerString[4] = cursor.getString(cursor
							.getColumnIndexOrThrow(HLdb.COL_ANSWER2));
			answerString[8] = "2";
			if (iCorrectPos == 2) iCorrect = j; 
			break;
		case 2:
			answerString[4] = cursor.getString(cursor
							.getColumnIndexOrThrow(HLdb.COL_ANSWER3));
			answerString[8] = "3";
			if (iCorrectPos == 3) iCorrect = j;
			break;
			
		case 3:
			answerString[4] = cursor.getString(cursor
							.getColumnIndexOrThrow(HLdb.COL_ANSWER4));
			answerString[8] = "4";
			if (iCorrectPos == 4) iCorrect = j; 
			break;
		}
//		Log.e("RandomizeAnswers","Randomized: 4 is now " + answerString[8]);
		
	}
	answerString[0] = String.valueOf(iCorrectPos);

	
}
}
