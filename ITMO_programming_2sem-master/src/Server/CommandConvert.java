package Server;
import spacemarine.*;
import command.*;
import Exceptions.FailedCheckException;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import Exceptions.EndOfFileException;
import Exceptions.IncorrectFileNameException;

import java.util.Date;
import java.util.LinkedList;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.util.Collections;

public class CommandConvert {
	
	public static Writer switcher(CommandSimple com, Collection c) throws IOException, EndOfFileException {
        switch (com.getCurrent()) {
            case HELP:
                return help();
            case INFO:
                return info(c);
            case SHOW:
                return show(c);
            case ADD:
                return add(c, com);
            case UPDATE:
                return update(c, com);
            case REMOVE_BY_ID:
                return removeById(c, com);
            case CLEAR:
                return clear(c);
            case EXECUTE_SCRIPT:
                return executeScript(c, com);
            case REMOVE_FIRST:
                return removeFirst(c);
            case HEAD:
                return head(c);
            case REMOVE_HEAD:
                return remove_head(c);
            case REMOVE_ALL_BY_WEAPON_TYPE:
                return remove_all_by_weapon_type(c, com);
            case GROUP_COUNTING_BY_CHAPTER:
                return group_counting_by_chapter(c);
            case FILTER_LESS_THAN_LOYAL:
                return filter_less_than_loyal(c, com);
            default:
                Writer.writeln("����� ������� ���");
        }
        return new Writer();
    }

	/**
     * ���������� ���������� �� ���� ��������� ��������
     */
    public static Writer help() {
        Writer w = new Writer();
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

        w.addToList(false,"end");
        return w;
    }
    
    public static Writer info(Collection collection) {
        Writer w = new Writer();
        w.addToList(true, "��� ���������: " + collection.list.getClass().getName());
        w.addToList(true, "����������� ���������: " + collection.list.size());
        w.addToList(true, "�������� �������: " + collection.getDate());

        w.addToList(false,"end");
        return w;
    }
    
    
    /**
     * ��������� � ��������� ������ �� ���������� �����.
     * � ������� ���������� ������� � ����� �� ����, � ������� �� ������ ������������ � ������������� ������
     * @throws IOException 
     */
    public static Writer executeScript(Collection c, CommandSimple command) throws IOException {
        Writer w = new Writer();
        String s = (String) command.returnObj();
        boolean programIsWorking = true;
        try (Reader reader = new Reader(s)) {
            if (RecursionHandler.isContains(s)) {
                RecursionHandler.addToFiles(s);
                String[] com;
                w.addToList(false, "\u001B[33m" + "������ ������� � ����� " + s + ": " + "\u001B[0m");
                String line = reader.read(w);
                while (line != null && programIsWorking) {
                    com = CommandReader.splitter(line);
                    programIsWorking = Command.switcher(w, reader, c, com[0], com[1]);
                    w.addToList(false, "\u001B[33m" + "������ ������� � ����� " + s + ": " + "\u001B[0m");
                    line = reader.read(w);
                }
                RecursionHandler.removeLast();
            } else
                w.addToList(true, "\u001B[31m" + "������� ����������" + "\u001B[0m");

        } catch (Exceptions.IncorrectFileNameException e) {
            w.addToList(true, "\u001B[31m" + "�������� ��� �����" + "\u001B[0m");
        } catch (Exceptions.EndOfFileException e) {
            w.addToList(true, "\u001B[31m" + "����������� ����� ����� " + s + "\u001B[0m");
            RecursionHandler.removeLast();
        } catch (FileNotFoundException e) {
            w.addToList(true, "\u001B[31m" + "���� �� ������" + "\u001B[0m");
        } catch (FailedCheckException | NumberFormatException e) {
            w.addToList(true, "\u001B[31m" + "���� �������� ������������ ������" + "\u001B[0m");
            RecursionHandler.removeLast();
        }

        w.addToList(false,"end");
        return w;
    }
    
    /**
     * �������������� ������� ������ � ��������� id
     */
    public static Writer update(Collection c, CommandSimple com) {
        Writer w = new Writer();
        Long id = ((SpaceMarine) com.returnObj()).getId();
        SpaceMarine sm = c.searchById(id);
        if (sm == null) {
            w.addToList(true, "������ �������� ���");
            w.addToList(false,"end");
        }
        else {
        c.list.set(c.list.indexOf(sm), (SpaceMarine) com.returnObj());
        Collections.sort(c.list);
        w.addToList(true, "������� � id: " + id + " ������� �������");

        w.addToList(false,"end"); }
        return w;
    }
    
    /**
     * ������� ��� �������� �� ���������
     */
    public static Writer clear(Collection c) {
        Writer w = new Writer();
        c.list.clear();
        w.addToList(true, "������ � ��������� ��� ���������");
        w.addToList(false,"end");
        return w;
    }
    
    public static Writer removeById(Collection c, CommandSimple com) {
        Writer w = new Writer();
        Long id = (Long) com.returnObj();
        SpaceMarine smm = c.searchById(id);
        if (smm == null) {
            w.addToList(true, "������ �������� ���");

            w.addToList(false,"end");
            return w;
        }
        c.list.remove(smm);
        w.addToList(true, "������� � id: " + id + " ������� ������");
        Collections.sort(c.list);

        w.addToList(false,"end");
        return w;
    }
    
    /**
     * ������� ��� �������� ������
     */
    public static Writer show(Collection c) {
        Writer w = new Writer();
        if (c.list.isEmpty())
            w.addToList(true, "� ��������� ��� ���������");
        else
            c.list.forEach(r -> w.addToList(true, r.toString()));

        w.addToList(false,"end");
        return w;
    }
    
    public static SpaceMarine SM_ID(SpaceMarine sm, Long id) {
        sm.setId(id);
        return sm;
    }

    /**
     * ��������� ������� � ������
     */
    public static Writer add(Collection c, CommandSimple com) {
        Writer w = new Writer();
        Long id = c.getRandId();
        c.list.add(SM_ID((SpaceMarine) com.returnObj(), id));
        Collections.sort(c.list);
        w.addToList(true, "������� � id: " + id + " �������� � ���������");
        w.addToList(false,"end");
        return w;
    }
    
    
    
    //remove_first : ������� ������ ������� �� ���������
    public static Writer removeFirst(Collection c) throws EndOfFileException {
    	Writer w = new Writer();
    	int size = c.list.size();
        int i = 0;
        if (i < size) {
                c.list.remove(c.list.get(i));
            }
        else  {
        	w.addToList(true, "\u001B[31m" + "� ��������� ��� ���������" + "\u001B[0m");}
        Collections.sort(c.list);
        w.addToList(false,"end");
        return w;
    }
      //  head : ������� ������ ������� ���������
        public static Writer head(Collection c) throws EndOfFileException {
        	Writer w = new Writer();
        	int size = c.list.size();
            int i = 0;
            if (i < size) {
            	w.addToList(true, c.list.get(i).toString());
                }
            else {
            	w.addToList(true, "\u001B[31m" + "� ��������� ��� ���������" + "\u001B[0m");
        }
            w.addToList(false,"end");
            return w;
        }
    //remove_head : ������� ������ ������� ��������� � ������� ���
            public static Writer remove_head(Collection c) throws EndOfFileException {
            	Writer w = new Writer();
            	int size = c.list.size();
                int i = 0;
                if (i < size) {
                	w.addToList(true, c.list.get(i).toString());
                	c.list.remove(c.list.get(i));
                	// Collections.sort(c.list);
                    }
                else {
                	w.addToList(true, "\u001B[31m" + "� ��������� ��� ���������" + "\u001B[0m");
            }
                w.addToList(false,"end");
                return w;
                }
            //group_counting_by_chapter
            
       public static Writer group_counting_by_chapter(Collection c) {
    	   Writer w = new Writer();     
    	   if (c.list.isEmpty()) {
                	w.addToList(true, "� ��������� ��� ���������");
                	w.addToList(false,"end");
                	return w;
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
                	w.addToList(false,"end");
                	return w;
               
            }}            
                /**
                 * remove_all_by_weapon_type
                 * @param w 
                 */
    public static Writer remove_all_by_weapon_type(Collection c, CommandSimple com) {
    	Writer w = new Writer();
    	String s = (String) com.returnObj();
        if (s.equals("HEAVY_BOLTGUN") || s.equals("BOLT_RIFLE") || s.equals("PLASMA_GUN") || s.equals("COMBI_PLASMA_GUN") || s.equals("INFERNO_PISTOL"))
        {
        	
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
        	w.addToList(true, "\u001B[32m" + "..." + "\u001B[32m");
        	w.addToList(false,"end");
        }
        else {
        	w.addToList(true, "\u001B[32m" + "������ ���� ������ ���� ���!" + "\u001B[32m");
        	w.addToList(false,"end");
            return w;
        	}
		return w;
                }

            
                /**
                 * filter_less_than_loyal
                 * @param w 
                 */
                public static Writer filter_less_than_loyal(Collection c, CommandSimple com) throws EndOfFileException {
                	Writer w = new Writer();
                	String s = (String) com.returnObj();
                	if (s == "0") w.addToList(true, "����� ��������� ���");
                    else {
                    	for (SpaceMarine sm : c.list) {
                            if (sm.getLoyal() == false)
                            	w.addToList(true, sm.toString());;
                        }
                    }
                    Collections.sort(c.list);
                    w.addToList(false,"end");
                    return w;
                }
                

	
	
}
