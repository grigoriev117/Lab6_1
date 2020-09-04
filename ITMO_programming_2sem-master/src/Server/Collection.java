package Server;

import spacemarine.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * ����� ��� �������� � ��������� LinkedList
 */
public class Collection {

	
	
    /**
     * ���� �������� ������
     */
    private Date date = new Date();
    /**
     * ������, � ������� �������� �������� ���� SpaceMarine
     */
    public LinkedList<SpaceMarine> list = new LinkedList<>();

    /**
     * �����, ������������ ������, ������� ��� ���������� � ������ CSV
     * @throws IOException 
     */
    public static Collection startFromSave(String[] args) throws IOException {
        if (args.length > 0) {
            File saveFile = new File(args[0]);
            if (saveFile.exists()) {
                SaveManagement.setFile(saveFile);
                return SaveManagement.listFromSave();
            }
        }
        return new Collection();
    }

    /**
     * �����, �������������� ����� �������� �� id
     */
    public SpaceMarine searchById(Long id) {
        for (SpaceMarine r : list) {
            if (r.getId().equals(id))
                return r;
        }
        return null;
    }

    public Date getDate() {
        return date;
    }

    /**
     * �����, ������������ ���������� id
     */
    public Long getRandId() {
        Long id;
        do {
            id = (long) (1 + Math.random() * (Long.MAX_VALUE - 1));
        } while (this.searchById(id) != null);
        return id;
    }
}