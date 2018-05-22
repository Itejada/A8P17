package com.company.manager;

import com.company.model.Corredor;
import com.company.model.Equip;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class ManagerCorredors {
    static Corredor[] corredors = new Corredor[100];

    public static Corredor inscriureCorredor(String nom, Equip equip) throws IOException {
        if(equip == null){
            return null;
        }

      try {
            FileWriter fileWriter= new FileWriter("corredores.txt", true);
            fileWriter.write(nom+":");
            fileWriter.write(String.valueOf(equip.id+":"));
            fileWriter.write(String.valueOf(obtenirNumeroCorredors()+1 +"\n"));
            fileWriter.close();

      }catch (IOException e) {
         e.printStackTrace();
      }


        return null;
    }

    public static Corredor obtenirCorredor(int id){

        try {
            BufferedReader fileReader= new BufferedReader(new FileReader("corredores.txt"));
            char[] c=new char[10];
            String lineaCorredor;
            while ((lineaCorredor = fileReader.readLine()) != null){
               String[] partes = lineaCorredor.split(":");

               if ( id== Integer.parseInt(partes[2])){
                   Corredor corredor =new Corredor(partes[0],Integer.parseInt(partes[1]));
                   corredor.id = id;
                   return corredor;

               }

                System.out.println(lineaCorredor);

            }

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
        e.printStackTrace();

    }

        return null;
    }

    public static Corredor[] obtenirLlistaCorredors(){
        Corredor[] llistaCorredors = new Corredor[obtenirNumeroCorredors()];

        try {
            Corredor[] corredors = new Corredor[obtenirNumeroCorredors()];
            BufferedReader fileReader = new BufferedReader(new FileReader("corredors.txt"));
            String lineaCorredor;
            while ((lineaCorredor = fileReader.readLine()) != null){
                String[] partes = lineaCorredor.split(":");
                 Corredor corredor = new Corredor(partes[0],Integer.parseInt(partes[1]));
                 corredor.id = Integer.parseInt(partes[2]);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return llistaCorredors;
    }

    public static Corredor[] buscarCorredorsPerNom(String nom){
        Corredor[] llistaCorredors = new Corredor[obtenirNumeroCorredorsPerNom(nom)];

        int j = 0;
        for (int i = 0; i < corredors.length; i++) {
            if(corredors[i] != null && corredors[i].nom.toLowerCase().contains(nom.toLowerCase())){
                llistaCorredors[j] = corredors[i];
                j++;
            }
        }

        return llistaCorredors;
    }

    public static boolean existeixCorredor(String nom){
        try {
            BufferedReader fileReader= new BufferedReader(new FileReader("corredores.txt"));
            char[] c=new char[10];
            String lineaCorredor;
            while ((lineaCorredor = fileReader.readLine()) != null){
                String[] partes = lineaCorredor.split(":");

                if ( nom.toLowerCase().equals(partes[0])){

                    return true;

                }
                System.out.println(lineaCorredor);

            }

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }


        return false;
    }

    public static void modificarNomCorredor(int id, String nouNom){

        try{
        BufferedReader fileReader= new BufferedReader(new FileReader("corredores.txt"));
        FileWriter fileWriter= new FileWriter("tmp_corredores.txt", true );

            char[] c=new char[10];
        String lineaCorredor;
        while ((lineaCorredor = fileReader.readLine()) != null) {
            String[] partes = lineaCorredor.split(":");

            if (id == Integer.parseInt(partes[2])) {
           fileWriter.write(nouNom);
           fileWriter.write(partes[1]);
           fileWriter.write(partes[2]);
           fileWriter.flush();


            }else {
                fileWriter.write(lineaCorredor);
            }
        }
        fileWriter.close();
        fileReader.close();
            /*  aqui estamos moviendo el fichero temporal al original()  */
            Files.move(FileSystems.getDefault().getPath("tmp_corredores.txt"),FileSystems.getDefault().getPath("corredores.txt"), REPLACE_EXISTING);

    }catch (IOException e){

        }
}
    public static void modificarEquipCorredor(int id, Equip nouEquip){
        if(nouEquip == null){
            return;
        }

        for (int i = 0; i < corredors.length; i++) {
            if(corredors[i] != null && corredors[i].id == id){
                corredors[i].idEquip = nouEquip.id;
            }
        }
    }

    public static void esborrarCorredor(int id){
        for (int i = 0; i < corredors.length; i++) {
            if(corredors[i] != null && corredors[i].id == id){
                corredors[i] = null;
            }
        }
    }

    private static int obtenirUltimIdCorredor(){
        int maxId = 0;
        for (int i = 0; i < corredors.length; i++) {
            if(corredors[i] != null && corredors[i].id > maxId){
                maxId = corredors[i].id;
            }
        }

        return maxId;
    }

    private static int obtenirNumeroCorredors(){

        int count= 0;
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader("corredores.txt"));
            while ((fileReader.readLine()) != null){
                count+=1;
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return count;
    }

    private static int obtenirNumeroCorredorsPerNom(String nom){
        int count = 0;
        for (int i = 0; i < corredors.length; i++) {
            if(corredors[i] != null && corredors[i].nom.toLowerCase().contains(nom.toLowerCase())){
                count++;
            }
        }

        return count;
    }
}
