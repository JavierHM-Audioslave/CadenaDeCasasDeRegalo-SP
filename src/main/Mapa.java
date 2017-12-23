package main;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.omg.Messaging.SyncScopeHelper;

public class Mapa {
	
	private Grafo grafo;
	private Integer cantLugares;
	private File salida;
	
	public Mapa(File entrada, File salida)
	{
		this.salida = salida;
		Scanner sc;
		try
		{
			sc = new Scanner(entrada);
			cantLugares = sc.nextInt();
			Integer[][] matAdy = new Integer[cantLugares][cantLugares];
			
			for(int i = 0; i<cantLugares; i++)
			{
				sc.nextInt();
				Integer lugar = sc.nextInt();
				while(lugar!=-1)
				{
					matAdy[i][lugar-1] = 1;
					matAdy[lugar-1][i] = 1;					
					lugar = sc.nextInt();
				}
			}
			
			for(int i = 0; i<cantLugares; i++)
			{
				for(int j = 0; j<cantLugares; j++)
				{
					if(matAdy[i][j]==null)
					{
						matAdy[i][j] = 1000000;
					}
					else
					{
						if(i==j)
						{
							matAdy[i][j] = 0;
						}
					}
				}
			}
			
			grafo = new Grafo(cantLugares, matAdy);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
	public void resolver()
	{
		/*Warshall warshall = new Warshall(grafo); // Todo este bloque era la resolucion a traves de aplicar Warshall pero aplicar ese algoritmo no me sirve para lo que requiere el ejercicio asi que dejo lo hecho comentado para no olvidarme del error que tuve pensando que Warshall me ayudaba a solucionar este ejercicio. //
		Boolean[][] dev = warshall.resolver();
		Integer cant;
		Integer cantPosta = 0;
		int fila = 0;
		for(int i = 0; i<cantLugares; i++)
		{
			cant = 0;
			for(int j = 0; j<cantLugares; j++)
			{
				if(dev[i][j] == false)
				{
					cant++;
				}
			}
			
			if(cant>cantPosta)
			{
				fila = i;
				cantPosta = cant;
			}
		}
		
		System.out.println("Fila: "+fila);
		System.out.println("Cantidad posta: "+cantPosta);*/
		
		
		Coloreo coloreo = new Coloreo(grafo);
		HashMap<Integer,String> salida = coloreo.colorear();
		Integer colorDeMayorActual = -1;
		Integer mayorActual = 0;
		Integer largoDeLinea;
		
		for(Map.Entry<Integer, String> linea : salida.entrySet())
		{
			largoDeLinea = linea.getValue().length();
			if(largoDeLinea > mayorActual)
			{
				mayorActual = largoDeLinea;
				colorDeMayorActual = linea.getKey();
			}
		}
		
		Integer aux;
		String auxaux;
		String[] sumarUnoACadaNodo = salida.get(colorDeMayorActual).split(" ");
		for(int i = 0; i<sumarUnoACadaNodo.length; i++)
		{
			aux = Integer.parseInt(sumarUnoACadaNodo[i]);
			aux++;
			auxaux = String.valueOf(aux);
			sumarUnoACadaNodo[i] = auxaux;
		}
		
		String definitivoSumarUnoACadaNodo = "";
		for(int i = 0; i<sumarUnoACadaNodo.length; i++)
		{
			definitivoSumarUnoACadaNodo = definitivoSumarUnoACadaNodo+" "+sumarUnoACadaNodo[i];
		}
		
		
		//System.out.println(salida.get(colorDeMayorActual).length()/2+1+"\n"+definitivoSumarUnoACadaNodo);
		imprimir(sumarUnoACadaNodo);		
	}
	
	private void imprimir(String[] sumarUnoACadaNodo)
	{
		FileWriter fw;
		PrintWriter pw;
		try
		{
			fw = new FileWriter(salida);
			pw = new PrintWriter(fw);
			
			pw.println(sumarUnoACadaNodo.length);
			for(int i = 0; i<sumarUnoACadaNodo.length; i++)
			{
				pw.print(sumarUnoACadaNodo[i]+" ");
			}
			
			try
			{
				pw.close();
				fw.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.exit(1);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

}
