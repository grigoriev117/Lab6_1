package Server;

import Exceptions.EndOfFileException;
import Exceptions.IncorrectFileNameException;
import spacemarine.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * ����� ��� ���������� ������ � �����
 */
public class Reader extends CommandReader {
    public Reader(String file) throws IncorrectFileNameException, FileNotFoundException {

        File f = new File(file);
        if (!f.exists())
            throw new IncorrectFileNameException("������! ���� �� ������!");
        scan = new Scanner(new File(file));
    }

    /**
     * ���������� ������
     */
    @Override
    public String read(Writer w) /*throws EndOfFileException */ {
        if (scan.hasNextLine()) {
            String line = scan.nextLine();
            System.out.print(line + "\n");
            return line;
        }
        System.out.print("����� �����." + "\n");
        return null; //������������� ������������� "_"
        //throw new EndOfFileException("��������������� ����� �����!");
    }

	
}