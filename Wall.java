import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Polygon;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class Wall {

	int[] xx;
	int[] yy;
	String letter;
	Color color;

	public Wall (int[] x, int[] y, Color color){

		xx=x;
		yy=y;
		this.color=color;

	}

	public Polygon getPoly (){

		return new Polygon(yy,xx,yy.length);
	}


	public String getLetter() {

		return letter;

	}

	public Color getColor()
	{
		return color;
	}



}