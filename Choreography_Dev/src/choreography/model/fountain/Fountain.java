/**
 * 
 */
package choreography.model.fountain;

import choreography.model.cannon.CannonEnum;
import choreography.model.cannon.IndependentCannon;
import choreography.model.cannon.CannonFactory;

/**
 * @author madridf
 *
 */
public class Fountain {

        private static Fountain instance;
		/**
		 *
		 * @return 
		 */
		public static synchronized Fountain getInstance() {
		        if (instance == null) {
		            instance = new Fountain();
		        }
		        return instance;
		    } 
		private IndependentCannon peacock; 
		private IndependentCannon spout;
		private IndependentCannon bazooka; 
		private IndependentCannon ftCurt;
		private IndependentCannon bkCurt; private ModuleGroup A;

    private ModuleGroup B;
	
	private Fountain(){
            A = (CannonFactory.createModuleGroup("A"));
            B = (CannonFactory.createModuleGroup("B"));
		
            initIndependentCannons();
	}

	/**
	 * @return the a
	 */
	public ModuleGroup getA() {
		return A;
	}


	/**
	 * @return the b
	 */
	public ModuleGroup getB() {
		return B;
	}

	/**
	 * @return the bazooka
	 */
	public IndependentCannon getBazooka() {
		return bazooka;
	}

	/**
	 * @return the bkCurt
	 */
	public IndependentCannon getBkCurt() {
		return bkCurt;
	}

	/**
	 * @return the ftCurt
	 */
	public IndependentCannon getFtCurt() {
		return ftCurt;
	}

	/**
	 * @return the module1
	 */
	public Module getModule1() {
		return A.getModules()[0];
	}

	/**
	 * @return the module1
	 */
	public Module getModule2() {
		return B.getModules()[0];
	}

	/**
	 * @return the module1
	 */
	public Module getModule3() {
		return A.getModules()[1];
	}

	/**
	 * @return the module1
	 */
	public Module getModule4() {
		return B.getModules()[1];
	}

	/**
	 * @return the module1
	 */
	public Module getModule5() {
		return A.getModules()[2];
	}

	/**
	 * @return the module1
	 */
	public Module getModule6() {
		return B.getModules()[2];
	}

	/**
	 * @return the module1
	 */
	public Module getModule7() {
		return A.getModules()[3];
	}

	/**
	 * @return the peacock
	 */
	public IndependentCannon getPeacock() {
		return peacock;
	}
	
	/**
	 * @return the spout
	 */
	public IndependentCannon getSpout() {
		return spout;
	}

	/**
	 * 
	 */
	private void initIndependentCannons() {
		peacock = CannonFactory.createIndependentCannon(CannonEnum.PEACOCK); 
		spout = CannonFactory.createIndependentCannon(CannonEnum.SPOUT); 
		bazooka = CannonFactory.createIndependentCannon(CannonEnum.BAZOOKA); 
		ftCurt = CannonFactory.createIndependentCannon(CannonEnum.FTCURT); 
		bkCurt = CannonFactory.createIndependentCannon(CannonEnum.BKCURT);
	}
	
	/**
	 * @param a the a to set
	 */
	protected void setA(ModuleGroup a) {
		A = a;
	}

	/**
	 * @param b the b to set
	 */
	protected void setB(ModuleGroup b) {
		B = b;
	}
	
	/**
	 * @param bazooka the bazooka to set
	 */
	public void setBazooka(IndependentCannon bazooka) {
		this.bazooka = bazooka;
	}

	/**
	 * @param bkCurt the bkCurt to set
	 */
	public void setBkCurt(IndependentCannon bkCurt) {
		this.bkCurt = bkCurt;
	}
	/**
	 * @param ftCurt the ftCurt to set
	 */
	public void setFtCurt(IndependentCannon ftCurt) {
		this.ftCurt = ftCurt;
	}

	/**
	 * @param module1 the module1 to set
	 */
	public void setModule1(Module module1) {
		A.getModules()[0] = module1;
	}

	/**
     * @param module2
	 */
	public void setModule2(Module module2) {
		B.getModules()[0] = module2;
	}

	/**
	 * @param module1 the module1 to set
	 */
	public void setModule3(Module module1) {
		A.getModules()[1] = module1;
	}

	/**
	 * @param module1 the module1 to set
	 */
	public void setModule4(Module module1) {
		B.getModules()[1] = module1;
	}

	/**
	 * @param module1 the module1 to set
	 */
	public void setModule5(Module module1) {
		A.getModules()[2] = module1;
	}

	/**
	 * @param module1 the module1 to set
	 */
	public void setModule6(Module module1) {
		B.getModules()[2] = module1;
	}

	/**
	 * @param module1 the module1 to set
	 */
	public void setModule7(Module module1) {
		A.getModules()[3] = module1;
	}

	/**
	 * @param peacock the peacock to set
	 */
	public void setPeacock(IndependentCannon peacock) {
		this.peacock = peacock;
	}

	/**
	 * @param spout the spout to set
	 */
	public void setSpout(IndependentCannon spout) {
		this.spout = spout;
	}
}
