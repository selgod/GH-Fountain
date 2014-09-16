/**
 * 
 */
package choreography.model.fountain;

import choreography.model.fountain.Module;
import choreography.model.cannon.CannonEnum;
import choreography.model.cannon.Cannon;
import java.util.ArrayList;

/**
 * @author madridf
 *
 */
public class ModuleGroup {
    String AB;
    Module[] modules;

    /**
     *
     * @param modulesIn
     */
    public ModuleGroup(Module ... modulesIn) {
        if(modulesIn.length == 4){
                this.modules = new Module[4];
                this.modules = modulesIn;
                AB = "A";
        } else if(modulesIn.length == 3){
                this.modules = new Module[3];
                this.modules = modulesIn;
                AB = "B";
        } 
    }

    /**
     *
     * @param e
     * @return
     */
    public ArrayList<Cannon> getCannonGroup(CannonEnum e) {
        ArrayList<Cannon> cg = new ArrayList<>();
        for(Module m : modules) {
            switch(e){
                case RING1:
                    cg.add(m.getR1());
                    break;
                case RING2:
                    cg.add(m.getR2());
                    break;
                case RING3:
                    cg.add(m.getR3());
                    break;
                case RING4:
                    cg.add(m.getR4());
                    break;
                case RING5:
                    cg.add(m.getR5());
                    break;
                case MULTI:
                    cg.add(m.getMx());
                    break;
                case CANDELABRA:
                    cg.add(m.getCandle());
                    break;
                case SWEEP:
                    cg.add(m.getSw());
                        default:
                        break;
            }
        }
        return cg;
    }
        
	/**
	 * @return the aB
	 */
	protected String getAB() {
		return AB;
	}

	/**
	 * @param aB the aB to set
	 */
	protected void setAB(String aB) {
		AB = aB;
	}

	/**
	 * @return the modules
	 */
	public Module[] getModules() {
		return modules;
	}

	/**
	 * @param modules the modules to set
	 */
	protected void setModules(Module[] modules) {
		this.modules = modules;
	}

    /**
     *
     * @return
     */
    @Override
        public String toString() {
            if(AB.equals("A")){
                return "ModuleA";
            }
            else
                return "ModuleB";
        }
}
