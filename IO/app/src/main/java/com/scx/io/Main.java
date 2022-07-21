package com.scx.io;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

public class Main {
    public static void main(String[] args) {
//        ioWrite();
//        ioRead();
//        ioCopy();
//        okIoRead();
        okIoCopy();
    }

    private static void ioRead() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("text.txt")))){
            String message;
            while ((message = bufferedReader.readLine()) != null) {
                System.out.println(message);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ioWrite() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("text.txt")))){
            bufferedWriter.write("abc");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void ioCopy() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("text.txt")));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("new_text.txt")))){
            String message;
            while ((message = bufferedReader.readLine()) != null) {
                bufferedWriter.write(message + "\n");
                bufferedWriter.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void okIoRead() {

        try (BufferedSource source = Okio.buffer(Okio.source(new File("text.txt")))){
            String message;
            while ((message = source.readUtf8Line()) != null) {
                System.out.println(message);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void okIoCopy() {
        try (BufferedSource bufferedSource = Okio.buffer(Okio.source(new File("text.txt")));
             BufferedSink bufferedSink = Okio.buffer(Okio.sink(new File("new_text.txt")))){
            bufferedSink.writeAll(bufferedSource);
//            Buffer buffer = new Buffer();
//            long read;
//            while ((read = bufferedSource.read(buffer, 1024)) != -1) {
//                bufferedSink.write(buffer, read);
//            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
