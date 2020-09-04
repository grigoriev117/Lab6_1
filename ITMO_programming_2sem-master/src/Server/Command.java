package Server;
import Exceptions.FailedCheckException;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import Exceptions.EndOfFileException;
import Exceptions.IncorrectFileNameException;
import spacemarine.*;

import java.util.Date;
import java.util.LinkedList;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.util.Collections;
/**
 * ����� ��� ��������� ������, �������� � �������
 */

public class Command {
	

    /**
     * ��������� ������
     * @throws IOException 
     * @throws NumberFormatException 
     * @throws FailedCheckException 
     */
    public static boolean switcher(Writer w, CommandReader reader, Collection c, String s1, String s2) throws EndOfFileException, NumberFormatException, IOException, FailedCheckException {
        switch (s1) {
            case ("help"):
                help(w);
                break;
            case ("info"):
                info(w, c);
                break;
            case ("show"):
                show(w, c);
                break;
            case ("add"):
                add(w, reader, c, s2);
                break;
            case ("update"):
                update(w, reader, c, s2);
                break;
            case ("remove_by_id"):
                removeById(w, reader, c, s2);
                break;
            case ("clear"):
                clear(w, c);
                break;
            case ("upload"):
                upload(w, reader, c, s2);
                break;
            case ("execute_script"):
                return executeScript(w, c, s2);
            case ("exit"):
                return false;
            case ("remove_first"):
            	removeFirst(w, c);
                break;
            case ("head"):
            	head(w, c);
                break;
            case ("remove_head"):
            	remove_head(w, c);
                break;
            case ("remove_all_by_weapon_type"):
            	remove_all_by_weapon_type(w, reader, c, s2);
                break;
            case ("group_counting_by_chapter"):
            	group_counting_by_chapter(w, c);
                break;
            case ("filter_less_than_loyal"):
            	filter_less_than_loyal(w, reader, c, s2);
                break;
            default:
            	w.addToList(true, "����� ������� �� ����������!\n ������� 'help', ����� ���������� ������ ��������� ������.");
        }
        return true;
    }
        
 interface Comparable<T> extends java.lang.Comparable<T> {

    }  


public static Checker<Boolean> boolCheck = (Boolean B) -> {
    if (B != null) return B;
    else throw new FailedCheckException();
};
    /**
     * help : ������� ������� �� ��������� ��������
     */
    public static void help(Writer w) {
    	w.addToList(true,
        				"help : ������� ������� �� ��������� ��������\n"+
    					"info : ������� � ����������� ����� ������ ���������� � ��������� (���, ���� �������������, ���������� ��������� � �.�.)\n"+
    					"show : ������� � ����������� ����� ������ ��� �������� ��������� � ��������� �������������\n"+
    					"add {element} : �������� ����� ������� � ���������\n"+
    					"update id {element} : �������� �������� �������� ���������, id �������� ����� ���������\n"+
    					"remove_by_id id : ������� ������� �� ��������� �� ��� id\n"+
    					"clear : �������� ���������\n"+
    					"save : ��������� ��������� � ����\n"+
    					"execute_script file_name : ������� � ��������� ������ �� ���������� �����.\n"+
    					"� ������� ���������� ������� � ����� �� ����, � ������� �� ������ ������������ � ������������� ������.\n"+
    					"exit : ��������� ��������� (��� ���������� � ����)\n"+
    					"remove_first : ������� ������ ������� �� ���������\n"+
    					"head : ������� ������ ������� ���������\n"+
    					"remove_head : ������� ������ ������� ��������� � ������� ���\n"+
    					"remove_all_by_weapon_type weaponType : ������� �� ��������� ��� ��������, �������� ���� weaponType �������� ������������ ���������\n"+
    					"group_counting_by_chapter : ������������� �������� ��������� �� �������� ���� chapter, ������� ���������� ��������� � ������ ������\n"+
    					"filter_less_than_loyal loyal : ������� ��������, �������� ���� loyal ������� ������ ���������\n"
                		
        );
    }
    
    //remove_first : ������� ������ ������� �� ���������
    public static void removeFirst(Writer w, Collection c) throws EndOfFileException {
        int size = c.list.size();
        int i = 0;
        if (i < size) {
                c.list.remove(c.list.get(i));
            }
        else  {
        	w.addToList(true, "\u001B[31m" + "� ��������� ��� ���������" + "\u001B[0m");}
        Collections.sort(c.list);
    }
      //  head : ������� ������ ������� ���������
        public static void head(Writer w, Collection c) throws EndOfFileException {
            int size = c.list.size();
            int i = 0;
            if (i < size) {
            	w.addToList(true, c.list.get(i).toString());
                }
            else {
            	w.addToList(true, "\u001B[31m" + "� ��������� ��� ���������" + "\u001B[0m");
        }
        }
    //remove_head : ������� ������ ������� ��������� � ������� ���
            public static void remove_head(Writer w, Collection c) throws EndOfFileException {
                int size = c.list.size();
                int i = 0;
                if (i < size) {
                	w.addToList(true, c.list.get(i).toString());
                	c.list.remove(c.list.get(i));
                	// Collections.sort(c.list);
                    }
                else {
                	w.addToList(true, "\u001B[31m" + "� ��������� ��� ���������" + "\u001B[0m");
            }}
            //group_counting_by_chapter
            
                        public static void group_counting_by_chapter(Writer w, Collection c) {
                if (c.list.isEmpty()) {
                	w.addToList(true, "� ��������� ��� ���������");
                    return;
                }
                else {
                	LinkedList<String> lan = new LinkedList<>();
                	//boolean contains(Object element);
                	for (SpaceMarine sm : c.list) {
                        if (!lan.contains(sm.getChapter().getParentLegion())) {
                        	lan.add(sm.getChapter().getParentLegion());
                        }
                	}
                	for (int j = 0; j < lan.size(); j++) {
                		w.addToList(true, "������:");
                		w.addToList(true, lan.get(j));
                		int k = 0;
                		for (SpaceMarine sm : c.list) {
                			if (sm.getChapter().getParentLegion().equals(lan.get(j))) {
                				w.addToList(true, sm.toString());
                				k = k+1;
                			}
                		}
                		w.addToList(true, "����� ���������: " + k);
                	}

               
            }}            
                /**
                 * remove_all_by_weapon_type
                 * @param w 
                 */
                public static void remove_all_by_weapon_type(Writer w, CommandReader reader, Collection c, String s) {
        if (s.equals("HEAVY_BOLTGUN") || s.equals("BOLT_RIFLE") || s.equals("PLASMA_GUN") || s.equals("COMBI_PLASMA_GUN") || s.equals("INFERNO_PISTOL"))
        {
		int size = c.list.size();
        	int i = 0;
		List<Integer> num = new ArrayList<Integer>();

                   for (SpaceMarine sm : c.list) {
                        if (sm.getWeaponType().toString().equals(s)) {
				num.add(i);
                        	 }
			else {
				i = i+1;
			}}
		for (int j = 0; j < num.size(); j++)
		{
		c.list.remove(c.list.get(num.get(j)));
                  }
		Collections.sort(c.list);
        }
        else {
        	w.addToList(true, "\u001B[32m" + "������ ���� ������ ���� ���!" + "\u001B[32m");
        	}
                }

            
                /**
                 * filter_less_than_loyal
                 * @param w 
                 */
                public static void filter_less_than_loyal(Writer w, CommandReader reader, Collection c, String s) throws EndOfFileException {
                    if (s == "0") w.addToList(true, "����� ��������� ���");
                    else {
                    	for (SpaceMarine sm : c.list) {
                            if (sm.getLoyal() == false)
                            	w.addToList(true, sm.toString());;
                        }
                    }
                    Collections.sort(c.list);
                }
                
                
             public static void upload(Writer w, CommandReader reader, Collection c, String s) throws EndOfFileException, NumberFormatException, IOException {
    	BufferedReader reader1 = new BufferedReader(new FileReader(s));
        // ��������� ���������
        String line = null;
        Scanner scanner = null;
        int index = 0;
        int cx = 1;
        Double cy = 1.0;
        String n1 = null;
        String n2 = null;
 
        while ((line = reader1.readLine()) != null) {
        	SpaceMarine sm = new SpaceMarine();
            scanner = new Scanner(line);
          
            
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String data = scanner.next();
                if (index == 0)
                    sm.setId(Long.parseLong(data));
                else if (index == 1)
                    sm.setName(data);
                else if (index == 2)
                	cx = Integer.parseInt(data.replace("Coordinates{x=", ""));
                else if (index == 3) {
                	cy = Double.parseDouble(data.replace("y=", "").replace("}", ""));
                    sm.setCoordinates(new Coordinates(cx, cy)); }
                else if (index == 4) {
                	String aa = data;
                	LocalDate creationTime = LocalDate.now();
                    sm.setCreationDate(creationTime);
                }
                else if (index == 5)
                	sm.setHealth(Double.parseDouble(data));
                else if (index == 6)
                	sm.setLoyal(Boolean.parseBoolean(data));
                else if (index == 7)
                	sm.setAchievements(data);
                else if (index == 8) {
                	String weaponType1 = data;
                	if (weaponType1.equals("HEAVY_BOLTGUN") || weaponType1.equals("BOLT_RIFLE") || weaponType1.equals("PLASMA_GUN") || weaponType1.equals("COMBI_PLASMA_GUN") || weaponType1.equals("INFERNO_PISTOL"))
                    {
                    	sm.setWeaponType(SpaceMarine.Weapon.valueOf(weaponType1));
                    }
                }
                else if (index == 9)
                	n1 = data.replace("Chapter{name=", "");
                else if (index == 10) {
                	n2 = data.replace("parentLegion=", "").replace("}", "");
                	sm.setChapter(new Chapter(n1, n2));
                }
                else
                	w.addToList(true, "/");
                index++;
            }
            index = 0;
            c.list.add(sm);
        }
         
        //��������� ��� �����
        reader.close();
    }      
                
    /**
     * ������� � ����������� ����� ������ ���������� � ���������
     * @param w 
     */
    public static void info(Writer w, Collection collection) {
    	w.addToList(true,"���: " + collection.list.getClass().getName());
    	w.addToList(true,"����������� ���������: " + collection.list.size());
    	w.addToList(true,"���� �������������: " + collection.getDate());
    }
    
    /**
     * ������� � ����������� ����� ������ ��� �������� ��������� � ��������� �������������
     * @param w 
     */
    public static void show(Writer w, Collection c) {
        if (c.list.isEmpty()) 
        	w.addToList(true,"� ��������� ��� ���������");
        else c.list.forEach(sm -> w.addToList(true, sm));
    }
    
    /**
     * �������� ������� � ���������
     * @param w 
     * @throws FailedCheckException 
     */
    public static void add(Writer w, CommandReader reader, Collection c, String s) throws EndOfFileException, FailedCheckException {
        Long id = c.getRandId();
        c.list.add(toAdd(w, reader, id, s));
        Collections.sort(c.list);
        
       
    }
    
    /**
     * �������� �������� �������� ���������, id �������� ����� ���������
     * @param w 
     * @throws FailedCheckException 
     */
    public static void update(Writer w, CommandReader reader, Collection c, String s) throws EndOfFileException, FailedCheckException {

        Long id = SpaceMarine.idCheck.checker(Long.parseLong(reader.read(w)));
        
        SpaceMarine smm = c.searchById(id);
        if (smm == null) {
        	w.addToList(true, "������� �� ������");
            return;
        }
        String name = SpaceMarine.nameCheck.checker(reader.read(w));
        //String name = reader.handlerS("������� String: name", SpaceMarine.nameCheck);
        c.list.set(c.list.indexOf(smm), toAdd(w, reader, id, name));
        
        Collections.sort(c.list);

        
    }
    
    /**
     * ������� ������� �� ��������� �� ��� id
     * @throws FailedCheckException 
     * @throws NumberFormatException 
     */
    public static void removeById(Writer w, CommandReader reader, Collection c, String s) throws EndOfFileException, NumberFormatException, FailedCheckException {
        Long id = SpaceMarine.idCheck.checker(Long.parseLong(s));
        
        SpaceMarine smm = c.searchById(id);
        if (smm == null) {
        	w.addToList(true,"������� �� ������");
            return;
        }
        c.list.remove(smm);
        Collections.sort(c.list);
        w.addToList(true, "������� � id: " + id + " ��� �����");
    }
    
    /**
     * ��������� ��������� � ����
     */
    public static void save(Collection c) {
        SaveManagement.saveToFile(c);
    }

    /**
     * ������� ��� �������� �� ���������
     * @param w 
     */
    public static void clear(Writer w, Collection c) {
        c.list.clear();
        w.addToList(true, "������ � ��������� ��� ���������!");
    }
    
     /**
     * ��������� � ��������� ������ �� ���������� �����.
     * � ������� ���������� ������� � ����� �� ����, � ������� �� ������ ������������ � ������������� ������
     * @param w 
     * @throws IOException 
     * @throws NumberFormatException 
     * @throws FailedCheckException 
     */
    public static boolean executeScript(Writer w, Collection c, String s) throws NumberFormatException, IOException, FailedCheckException {
        boolean programIsWorking = true;
        //Reader reader;
        try (Reader reader = new Reader(s)) {
            if (RecursionHandler.isContains(s)) {
                RecursionHandler.addToFiles(s);
                String[] com;
                w.addToList(false,"\u001B[36m" + "������ ������� � ����� " + s + ": " + "\u001B[36m");
                String line = reader.read(w);
                while (line != null && programIsWorking) {
                    com = CommandReader.splitter(line);
                    programIsWorking = Command.switcher(w, reader, c, com[0], com[1]);
                    w.addToList(false, "\u001B[33m" + "������ ������� � ����� " + s + ": " + "\u001B[0m");
                    line = reader.read(w);
                }
                RecursionHandler.removeLast();
            } else
            	w.addToList(true,"\u001B[31m" + "���-�� ����� �������� ��������? �� ����� �����!" + "\u001B[31m");

        } catch (IncorrectFileNameException e) {
        	w.addToList(true, "\u001B[31m" + "�������� ��� �����" + "\u001B[0m");
        } catch (EndOfFileException e) {
        	w.addToList(true, "\u001B[31m" + "����������� ����� ����� " + s + "\u001B[0m");
            RecursionHandler.removeLast();
        } catch (FileNotFoundException e) {
        	w.addToList(true, "\u001B[31m" + "���� �� ������" + "\u001B[0m");
        }
        return programIsWorking;
    }
    
    
    public static SpaceMarine toAdd(Writer w, CommandReader reader, Long id, String s) throws EndOfFileException, FailedCheckException {

        SpaceMarine sm = new SpaceMarine();
        sm.setId(id);
        sm.setName(SpaceMarine.nameCheck.checker(s));

        w.addToList(true,"��o� ����� Coordinates:");
        w.addToList(false, "������� int x: ");
        int cx = Coordinates.xCheck.checker(Integer.parseInt(reader.read(w)));
        w.addToList(false, "������� Double y: ");
        Double cy = Coordinates.yCheck.checker(Double.parseDouble(reader.read(w)));
        sm.setCoordinates(new Coordinates(cx, cy));

        LocalDate creationTime = LocalDate.now();
        sm.setCreationDate(creationTime);
        
        w.addToList(false, "������� Double health, ������ 0:");
        Double health1 = SpaceMarine.healthCheck.checker(Double.parseDouble(reader.read(w)));
        //Double health1 = reader.handlerD("������� Double health, ������ 0:", SpaceMarine.healthCheck);
        sm.setHealth(health1);

        w.addToList(false, "������� boolean loyal");
        boolean loyal1 = boolCheck.checker(Boolean.parseBoolean(reader.read(w)));
        //boolean loyal1 = reader.handlerB("������� boolean loyal", boolCheck);
        sm.setLoyal(loyal1);
        
        String achievements = SpaceMarine.nameCheck.checker(reader.read(w));
        //String achievements = reader.handlerS("������� String achievements", SpaceMarine.nameCheck);
        sm.setAchievements(achievements);

        w.addToList(true,"��o� ����� Chapter");
        w.addToList(false,"������� String name: ");
        String name1 = Chapter.cCheck.checker(reader.read(w));
        //String name1 = reader.handlerS("������� String name: ", Chapter.cCheck);
        w.addToList(false,"������� String parentLegion: ");
        String parentLegion1 = Chapter.cCheck.checker(reader.read(w));
        //String parentLegion1 = reader.handlerS("������� String parentLegion: ", Chapter.cCheck);
        sm.setChapter(new Chapter(name1, parentLegion1));
        
        w.addToList(false,"������� Weapon weaponType {\r\n" + 
        		"    HEAVY_BOLTGUN,\r\n" + 
        		"    BOLT_RIFLE,\r\n" + 
        		"    PLASMA_GUN,\r\n" + 
        		"    COMBI_PLASMA_GUN,\r\n" + 
        		"    INFERNO_PISTOL;\r\n" + 
        		"} ");
        String weaponType1 = SpaceMarine.nameCheck.checker(reader.read(w));
        
        if (weaponType1.equals("HEAVY_BOLTGUN") || weaponType1.equals("BOLT_RIFLE") || weaponType1.equals("PLASMA_GUN") || weaponType1.equals("COMBI_PLASMA_GUN") || weaponType1.equals("INFERNO_PISTOL"))
        {
        	sm.setWeaponType(SpaceMarine.Weapon.valueOf(weaponType1));
        }
        else {
        	w.addToList(true,"������� �� ������������");
        	w.addToList(true,"    HEAVY_BOLTGUN,\r\n" + 
    		"    BOLT_RIFLE,\r\n" + 
    		"    PLASMA_GUN,\r\n" + 
    		"    COMBI_PLASMA_GUN,\r\n" + 
    		"    INFERNO_PISTOL;\r\n"
    		);
        }

        return sm;
    }
}