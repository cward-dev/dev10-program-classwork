package learn.sf.data;

        import learn.sf.model.Panel;
        import learn.sf.model.PanelMaterial;

        import java.util.ArrayList;
        import java.util.List;

public class PanelFileRepositoryDouble implements PanelRepository {

    private ArrayList<Panel> panels = new ArrayList<>();

    public PanelFileRepositoryDouble() {
        Panel panel1 = new Panel("Bluegrass",3,15,1994,PanelMaterial.MULTICRYSTALLINE_SILICON,true);
        panel1.setPanelId(1);

        Panel panel2 = new Panel("Jazz",54,22,1983,PanelMaterial.AMORPHOUS_SILICON,false);
        panel2.setPanelId(2);

        Panel panel3 = new Panel("Gospel",13,15,2005,PanelMaterial.COPPER_INDIUM_GALLIUM_SELENIDE,true);
        panel3.setPanelId(3);

        Panel panel4 = new Panel("Jazz",54,10,2016,PanelMaterial.AMORPHOUS_SILICON,false);
        panel4.setPanelId(4);


        panels.add(panel1);
        panels.add(panel2);
        panels.add(panel3);
        panels.add(panel4);
    }

    @Override
    public List<Panel> findAll() throws DataAccessException {
        return panels;
    }

    @Override
    public List<Panel> findBySection(String section) {
        ArrayList<Panel> result = new ArrayList<>();

        for (Panel p : panels) {
            if (p.getSection().equalsIgnoreCase(section)) {
                result.add(p);
            }
        }

        return result;
    }

    @Override
    public List<Panel> findByMaterial(PanelMaterial material) {
        ArrayList<Panel> result = new ArrayList<>();

        for (Panel p : panels) {
            if (p.getMaterial() == material) {
                result.add(p);
            }
        }

        return result;
    }

    @Override
    public Panel findById(int panelId) {
        for (Panel p : panels) {
            if (p.getPanelId() == panelId) {
                return p;
            }
        }

        return null;
    }

    @Override
    public Panel findByLocation(String section, int row, int column) throws DataAccessException {
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
    public List<String> getAllSections() {
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
    public Panel add(Panel panel) {
        panel.setPanelId(getNextId());
        panels.add(panel);

        return panel;
    }

    @Override
    public boolean update(Panel panel) {
        for (int i = 0; i < panels.size(); i++) {
            if (panels.get(i).getPanelId() == panel.getPanelId()) {
                panels.remove(i);
                panels.add(panel);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean deleteById(int panelId) {
        for (int i = 0; i < panels.size(); i++) {
            if (panels.get(i).getPanelId() == panelId) {
                panels.remove(i);
                return true;
            }
        }

        return false;
    }

    private int getNextId() {
        int nextId = 0;

        for (Panel p : panels) {
            nextId = Math.max(nextId, p.getPanelId());
        }

        return nextId + 1;
    }
}
