package learn.sf.data;

import learn.sf.model.Panel;
import learn.sf.model.PanelMaterial;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PanelFileRepository implements PanelRepository {

    private static final String DELIMITER = ",";
    private static final String DELIMITER_REPLACEMENT = "@@~@@~@@";
    private static final String HEADER = "panelId,section,row,column,yearInstalled,material,isTracking";
    private final String filePath;

    public PanelFileRepository(@Value("${dataFilePath:./data/solar-panels.csv}")String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Panel> findAll() throws DataAccessException {
        ArrayList<Panel> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                Panel panel = deserialize(line);
                if (panel != null) {
                    result.add(panel);
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("That file was not found.");
        } catch (IOException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        }

        return result;
    }

    @Override
    public List<Panel> findBySection(String section) throws DataAccessException {
        List<Panel> panels = findAll();
        ArrayList<Panel> result = new ArrayList<>();

        for (Panel p : panels) {
            if (p.getSection().equalsIgnoreCase(section)) {
                result.add(p);
            }
        }

        return result;
    }

    @Override
    public List<Panel> findByMaterial(PanelMaterial material) throws DataAccessException {
        List<Panel> panels = findAll();
        ArrayList<Panel> result = new ArrayList<>();

        for (Panel p : panels) {
            if (p.getMaterial() == material) {
                result.add(p);
            }
        }

        return result;
    }

    @Override
    public Panel findById(int panelId) throws DataAccessException {
        List<Panel> panels = findAll();

        for (Panel p : panels) {
            if (p.getPanelId() == panelId) {
                return p;
            }
        }

        return null;
    }

    @Override
    public Panel findByLocation(String section, int row, int column) throws DataAccessException {
        List<Panel> panels = findAll();

        for (Panel p : panels) {
            if (p.getSection().equalsIgnoreCase(section)
                        && p.getRow() == row
                        && p.getColumn() == column) {
                return p;
            }
        }

        return null;
    }

    @Override
    public List<String> getAllSections() throws DataAccessException {
        List<Panel> panels = findAll();
        ArrayList<String> result = new ArrayList<>();

        boolean isPresent;
        for (Panel p : panels) {
            if (result.size() == 0) {
                result.add(p.getSection());
            } else {
                isPresent = false;
                for (String s : result) {
                    if (s.equalsIgnoreCase(p.getSection())) {
                        isPresent = true;
                    }
                }
                if (!isPresent) {
                    result.add(p.getSection());
                }
            }
        }

        return result;
    }

    @Override
    public Panel add(Panel panel) throws DataAccessException {
        List<Panel> panels = findAll();

        panel.setPanelId(getNextId());

        panel = deserialize(serialize(panel)); // removes any @@@s and replaces them with a comma in panel object

        panels.add(panel);
        writeAll(panels);

        return panel;
    }

    @Override
    public boolean update(Panel panel) throws DataAccessException {
        List<Panel> panels = findAll();

        for (int i = 0; i < panels.size(); i++) {
            if (panels.get(i).getPanelId() == panel.getPanelId()) {
                panels.remove(i);
                panels.add(panel);
                writeAll(panels);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean deleteById(int panelId) throws DataAccessException {
        List<Panel> panels = findAll();

        for (int i = 0; i < panels.size(); i++) {
            if (panels.get(i).getPanelId() == panelId) {
                panels.remove(i);
                writeAll(panels);
                return true;
            }
        }

        return false;
    }

    private int getNextId() throws DataAccessException {
        int nextId = 0;
        List<Panel> panels = findAll();

        for (Panel p : panels) {
            nextId = Math.max(nextId, p.getPanelId());
        }

        return nextId + 1;
    }

    private void writeAll(List<Panel> panels) throws DataAccessException {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println(HEADER);
            for (Panel p : panels) {
                writer.println(serialize(p));
            }
        } catch (IOException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        }
    }

    private String serialize(Panel panel) {
        return String.format("%s,%s,%s,%s,%s,%s,%s",
                panel.getPanelId(),
                clean(panel.getSection()),
                panel.getRow(),
                panel.getColumn(),
                panel.getYearInstalled(),
                panel.getMaterial(),
                panel.isTracking()
        );
    }

    private Panel deserialize(String value) {
        String[] fields = value.split(DELIMITER, -1);

        if (fields.length == 7) {
            Panel panel = new Panel();
            panel.setPanelId(Integer.parseInt(fields[0]));
            panel.setSection(restore(fields[1]));
            panel.setRow(Integer.parseInt(fields[2]));
            panel.setColumn(Integer.parseInt(fields[3]));
            panel.setYearInstalled(Integer.parseInt(fields[4]));
            panel.setMaterial(PanelMaterial.valueOf(fields[5]));
            panel.setTracking(Boolean.parseBoolean(fields[6]));
            return panel;
        }
        return null;
    }

    private String clean(String value) {
        return value.replace(DELIMITER, DELIMITER_REPLACEMENT);
    }

    private String restore(String value) {
        return value.replace(DELIMITER_REPLACEMENT, DELIMITER);
    }

    private void sortAllPanels() throws DataAccessException {
        List<Panel> panels = findAll();

    }
}
