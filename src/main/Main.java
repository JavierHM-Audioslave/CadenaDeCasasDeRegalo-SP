package main;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		
		Mapa mapa = new Mapa(new File("in.txt"), new File("out.txt"));
		mapa.resolver();

	}

}
