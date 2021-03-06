package com.company.manager;

import com.company.model.Equip;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;

import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;

public class ManagerEquips2 {
    static Equip[] equips = new Equip[100];
    static int MAXNOM = 12;
    static int MAXID = 4;
    public static Equip inscriureEquip(String nom) {
        byte nomBytes[] = (nom + ":").getBytes();
        ByteBuffer outNom = ByteBuffer.wrap(nomBytes);

        byte idBytes[] = (String.valueOf(obtenirNumeroEquips()+1) + "\n").getBytes();
        ByteBuffer outId = ByteBuffer.wrap(idBytes);



        try (FileChannel fc = (FileChannel.open(FileSystems.getDefault().getPath("equips.txt"), READ, WRITE))) {
            long posicionFinal = fc.size();
            fc.position(posicionFinal);

            fc.write(outNom);
            int id = (int) posicionFinal/(MAXNOM+MAXID)+1;
            ByteBuffer byteBuffer = ByteBuffer.allocate(MAXID); //
            byteBuffer.putInt(0,id);

            fc.position(posicionFinal);
            fc.write(byteBuffer);
            fc.close();

        } catch (IOException x) {
            System.out.println("I/O Exception: " + x);
        }
        return null;
    }

    public static Equip obtenirEquip(int id){
        try (FileChannel fc = (FileChannel.open(FileSystems.getDefault().getPath("equips.txt"), READ, WRITE))) {
            fc.position((id-1)*(MAXNOM+MAXID));
            ByteBuffer byteBuffer = ByteBuffer.allocate(MAXNOM);

            fc.read(byteBuffer);

            String nom = new String(byteBuffer.array(), Charset.forName("UTF-8"));

            Equip equip = new Equip(nom);

            equip.id = id;

            return equip;

//////////////////////////////////////////
        } catch (IOException x) {
            System.out.println("I/O Exception: " + x);
        }

        return null;
    }

    public static Equip obtenirEquip(String nom){

        return null;
    }

    public static String obtenirNomEquip(int id){
        for (int i = 0; i < equips.length; i++) {
            if(equips[i] != null && equips[i].id == id){
                return equips[i].nom;
            }
        }

        return "";
    }

    public static Equip[] obtenirLlistaEquips(){
        Equip[] llistaEquips = new Equip[obtenirNumeroEquips()];

        int j = 0;
        for (int i = 0; i < equips.length; i++) {
            if(equips[i] != null){
                llistaEquips[j] = equips[i];
                j++;
            }
        }

        return llistaEquips;
    }

    public static Equip[] buscarEquipsPerNom(String nom){
        Equip[] llistaEquips = new Equip[obtenirNumeroEquipsPerNom(nom)];

        int j = 0;
        for (int i = 0; i < equips.length; i++) {
            if(equips[i] != null && equips[i].nom.toLowerCase().contains(nom.toLowerCase())){
                llistaEquips[j] = equips[i];
                j++;
            }
        }

        return llistaEquips;
    }

    public static boolean existeixEquip(String nom){
        for (int i = 0; i < equips.length; i++) {
            if(equips[i] != null && equips[i].nom.toLowerCase().equals(nom.toLowerCase())){
                return true;
            }
        }

        return false;
    }

    public static void modificarNomEquip(int id, String nouNom){
        for (int i = 0; i < equips.length; i++) {
            if(equips[i] != null && equips[i].id == id){
                equips[i].nom = nouNom;
            }
        }
    }

    public static void esborrarEquip(int id){
        for (int i = 0; i < equips.length; i++) {
            if(equips[i] != null && equips[i].id == id){
                equips[i] = null;
            }
        }
    }

    private static int obtenirUltimIdEquip(){
        int maxId = 0;
        for (int i = 0; i < equips.length; i++) {
            if(equips[i] != null && equips[i].id > maxId){
                maxId = equips[i].id;
            }
        }

        return maxId;
    }

    private static int obtenirNumeroEquips(){
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader("equips.txt"));
            String lineaEquipo;
            int equipos = 0;
            while ((lineaEquipo = fileReader.readLine()) != null) {
                equipos += 1;
            }
            return equipos;
        }catch (IOException e){
            e.printStackTrace();
        }
        return 0;
    }

    private static int obtenirNumeroEquipsPerNom(String nom){
        int count = 0;
        for (int i = 0; i < equips.length; i++) {
            if(equips[i] != null && equips[i].nom.toLowerCase().contains(nom.toLowerCase())){
                count++;
            }
        }

        return count;
    }
}
