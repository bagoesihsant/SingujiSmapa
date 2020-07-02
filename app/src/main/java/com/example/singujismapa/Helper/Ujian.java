package com.example.singujismapa.Helper;

public class Ujian {

    private int ID_UJIAN;
    private String NIP;
    private String ID_MAPEL;
    private String ID_JURUSAN;
    private String ID_JENIS_SOAL;
    private String ID_TIPE;
    private int JUMLAH_SOAL;
    private int WAKTU_MENGERJAKAN;
    private int WAKTU_MULAI;
    private String TOKEN_SOAL;
    private String STATUS_UJIAN;


    public Ujian()
    {
        //Basic Constructor needed for process
    }

    public int getID_UJIAN() {
        return ID_UJIAN;
    }

    public void setID_UJIAN(int ID_UJIAN) {
        this.ID_UJIAN = ID_UJIAN;
    }

    public String getNIP() {
        return NIP;
    }

    public void setNIP(String NIP) {
        this.NIP = NIP;
    }

    public String getID_MAPEL() {
        return ID_MAPEL;
    }

    public void setID_MAPEL(String ID_MAPEL) {
        this.ID_MAPEL = ID_MAPEL;
    }

    public String getID_JURUSAN() {
        return ID_JURUSAN;
    }

    public void setID_JURUSAN(String ID_JURUSAN) {
        this.ID_JURUSAN = ID_JURUSAN;
    }

    public String getID_JENIS_SOAL() {
        return ID_JENIS_SOAL;
    }

    public void setID_JENIS_SOAL(String ID_JENIS_SOAL) {
        this.ID_JENIS_SOAL = ID_JENIS_SOAL;
    }

    public String getID_TIPE() {
        return ID_TIPE;
    }

    public void setID_TIPE(String ID_TIPE) {
        this.ID_TIPE = ID_TIPE;
    }

    public int getJUMLAH_SOAL() {
        return JUMLAH_SOAL;
    }

    public void setJUMLAH_SOAL(int JUMLAH_SOAL) {
        this.JUMLAH_SOAL = JUMLAH_SOAL;
    }

    public int getWAKTU_MENGERJAKAN() {
        return WAKTU_MENGERJAKAN;
    }

    public void setWAKTU_MENGERJAKAN(int WAKTU_MENGERJAKAN) {
        this.WAKTU_MENGERJAKAN = WAKTU_MENGERJAKAN;
    }

    public int getWAKTU_MULAI() {
        return WAKTU_MULAI;
    }

    public void setWAKTU_MULAI(int WAKTU_MULAI) {
        this.WAKTU_MULAI = WAKTU_MULAI;
    }

    public String getTOKEN_SOAL() {
        return TOKEN_SOAL;
    }

    public void setTOKEN_SOAL(String TOKEN_SOAL) {
        this.TOKEN_SOAL = TOKEN_SOAL;
    }

    public String getSTATUS_UJIAN() {
        return STATUS_UJIAN;
    }

    public void setSTATUS_UJIAN(String STATUS_UJIAN) {
        this.STATUS_UJIAN = STATUS_UJIAN;
    }
}
