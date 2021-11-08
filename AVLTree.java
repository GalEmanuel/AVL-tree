/**
 *
 * AVLTree
 *
 * An implementation of a AVL Tree with
 * distinct integer keys and info
 *
 */

public class AVLTree {
	
	private AVLNode root=null;
	private int minkey=Integer.MAX_VALUE;
	private String mininfo;
	private int maxkey= Integer.MIN_VALUE;
	private String maxinfo;
  /**
   * public boolean empty()
   *
   * returns true if and only if the tree is empty
   *
   */
  public boolean empty() {
	  return (this.root==null); 
  }

 /**
   * public String search(int k)
   *
   * returns the info of an item with key k if it exists in the tree
   * otherwise, returns null
   */
  public String search(int k)
  {if(!this.empty()) {
	AVLNode res=this.Recsearch(k,this.root);
	return res.getValue();
  }
  else {return null;}
  }
  /**
   *private AVLNode Recsearch
   *
   * returns real AVLNode if the item exists in the tree
   * otherwise returns virtual leaf, 
   * which is the place for the real node if exsits
   */
  private  AVLNode Recsearch(int k, AVLNode node) {
	  if (node.getKey() ==k || node.getKey()==-1) {
		  return node;
	  }
	  if (node.getKey()>k) {
		  return Recsearch(k, (AVLNode)node.getLeft()); 
	  }
	  else {
		  return Recsearch(k, (AVLNode)node.getRight());
	  }
  }
  
  private void update_size(AVLNode node){ //update node size = node.left.size+node.right.size+1
	  if(node!=null) {
	  if (node.getKey()!=-1) {
		  node.setsize(((AVLNode) node.getLeft()).getsize()+((AVLNode) node.getRight()).getsize() +1);
	  }
	  else {
		  node.setsize(0);
	  }
	  }
  }
  
  private void update_all(AVLNode node) { //update size,height and rankdiffer
	  this.update_height(node);
	  this.update_rankdiffer(node);
	  this.update_size(node);
  }
  
  private void update_height(AVLNode node) { //update node height = max(node.left.height,node.right.height)+1
	  if(node!=null) {
	  if (node.getKey()!=-1) {
	  node.setHeight(Math.max(node.getLeft().getHeight(), node.getRight().getHeight() )+1); 
	  }
	  else {
		  node.setHeight(-1); // if node is virtual leaf 
	  }
  }
 
  }
  private void update_rankdiffer(AVLNode node) { //update node rankdiffer L and R = |node.height-node.son.height|
	  if (node!=null) {
	  if(node.getKey()==-1) {
		  node.setrankdifferL(0);
		  node.setrankdifferR(0);
	  }
	  else {
		  node.setrankdifferL(Math.abs(node.getHeight()-node.getLeft().getHeight()));
		  node.setrankdifferR(Math.abs(node.getHeight()-node.getRight().getHeight()));
	  }
	  }
	 
  }
 
  
  private void Rotate(AVLNode dad, AVLNode son, char der) {  //der = direction, do rotate right-'r'  or do rotate left-'l' 
	  if (der=='r') {										// rotate right , son is left son for dad
		  if (dad.getParent()!=null) {
		  if (dad.getParent().getLeft().getKey()==dad.getKey()) { // dad is left son
			  dad.getParent().setLeft(son); }
		  else {  dad.getParent().setRight(son);}}
		  son.getRight().setParent(dad);
		  son.setParent(dad.getParent());
		  dad.setParent(son);
		  dad.setLeft(son.getRight());
		  son.setRight(dad);
	  }
	  else if (der=='l') {										// rotate left , son is right son for dad
		  if (dad.getParent()!=null) {
		  if (dad.getParent().getLeft().getKey()==dad.getKey()) { // dad is left son
			  dad.getParent().setLeft(son); }
		  else {  dad.getParent().setRight(son);}}
		  son.getLeft().setParent(dad);
		  son.setParent(dad.getParent());
		  dad.setParent(son);
		  dad.setRight(son.getLeft());
		  son.setLeft(dad); 
		  
		  
		  
	  }                                                                                         
  }
  
  
  private void DoubleRotate(AVLNode grandson, AVLNode son, AVLNode dad,String der) {
	  if(der.equals("lr")) {		//"lr" means rotate left and then rotate right
		  this.Rotate(son,grandson,'l');	// rotate left
		  this.Rotate(dad,grandson,'r');	// rotate right
	  }
	  else if(der.equals("rl")) {		//"rl" means rotate right and then rotate left
		  this.Rotate(son,grandson,'r');	// rotate right
		  this.Rotate(dad,grandson,'l');	// rotate left
	  }
	}
  
  private int insert_rebalance(int rankdifferL, int rankdifferR,AVLNode dad,int count) {
	  if ((dad.getrankdifferL()==0 && dad.getrankdifferR()==1)||                   //case1
		(dad.getrankdifferL()==1 && dad.getrankdifferR()==0)) {
		 count += this.insert_rebalance_case1(dad.getrankdifferL(),dad.getrankdifferR(),dad,count)  ;  
	  }
	  else if(dad.getrankdifferL()==0 && dad.getrankdifferR()==2) {//left son is the problematic one case2+3
		 if( (((AVLNode)dad.getLeft()).getrankdifferL()==1 && ((AVLNode)dad.getLeft()).getrankdifferR()==2) || //case2
			 (((AVLNode)dad.getLeft()).getrankdifferL()==1 && ((AVLNode)dad.getLeft()).getrankdifferR()==1) ){ //newcase after join
			 count += this.insert_rebalance_case2(dad.getrankdifferL(),dad.getrankdifferR(),dad,count)  ;
		 }
		 else if(((AVLNode)dad.getLeft()).getrankdifferL()==2 && ((AVLNode)dad.getLeft()).getrankdifferR()==1) { //case3
			 count += this.insert_rebalance_case3(dad.getrankdifferL(),dad.getrankdifferR(),dad,count)  ;
		 }
	 }
	  else if(dad.getrankdifferL()==2 && dad.getrankdifferR()==0) {  //right son is the problematic one case2+3
		  if( (((AVLNode)dad.getRight()).getrankdifferL()==2 && ((AVLNode)dad.getRight()).getrankdifferR()==1) || //case2
			  (((AVLNode)dad.getRight()).getrankdifferL()==1 && ((AVLNode)dad.getRight()).getrankdifferR()==1) )  { //newcase after join
			  count += this.insert_rebalance_case2(dad.getrankdifferL(),dad.getrankdifferR(),dad,count)  ;
		  }
		  else if(((AVLNode)dad.getRight()).getrankdifferL()==1 && ((AVLNode)dad.getRight()).getrankdifferR()==2) { //case3
			  count += this.insert_rebalance_case3(dad.getrankdifferL(),dad.getrankdifferR(),dad,count)  ;
		  }
	 }
	return count;
  }
  
  private int insert_rebalance_case1(int rankdifferL, int rankdifferR,AVLNode dad, int count) { //rebalance after insert case1
	  count+=1; //one promote
	  this.update_all((AVLNode)dad);
	  this.update_rankdiffer((AVLNode)dad.getParent());
	  return count;}
  
  private int insert_rebalance_case2(int rankdifferL, int rankdifferR,AVLNode dad,int count) {
	  count+=2; //one rotate , one demote
	  if( ((dad.getrankdifferL()==0 && dad.getrankdifferR()==2)
		&&((AVLNode)dad.getLeft()).getrankdifferL()==1
		&& ((AVLNode)dad.getLeft()).getrankdifferR()==2) || //left son is the problematic one. case2
			  
		   ((dad.getrankdifferL()==0 && dad.getrankdifferR()==2)
		&&((AVLNode)dad.getLeft()).getrankdifferL()==1 
		&&((AVLNode)dad.getLeft()).getrankdifferR()==1) ) //only after join with left son problem	 
		 
		  {
		  this.Rotate(dad,(AVLNode)dad.getLeft(),'r'); //rotate right for dad and left son	
		  this.update_all((AVLNode)dad);//after rotate dad is the prevson
		  this.update_all((AVLNode)dad.getParent());
	   //fix size from prevdad.getparent() to the root
	  }
	  else if(((dad.getrankdifferL()==2 && dad.getrankdifferR()==0)
			&&((AVLNode)dad.getRight()).getrankdifferL()==2
			&&((AVLNode)dad.getRight()).getrankdifferR()==1) ||// right is the problematic one. case2
			  
			  ((dad.getrankdifferL()==2 && dad.getrankdifferR()==0)
			&&((AVLNode)dad.getRight()).getrankdifferL()==1 
			&&((AVLNode)dad.getRight()).getrankdifferR()==1) ) //only after join with right son problem
		  
		  {
		  this.Rotate(dad,(AVLNode)dad.getRight(),'l'); //rotate left for dad and right son
		  this.update_all((AVLNode)dad);//after rotate dad is the prevson
		  this.update_all((AVLNode)dad.getParent());		  
		  //fix size from prevdad.getparent() to the root	  
	  }
	  	  
	  return count;
  }
  
  private int insert_rebalance_case3(int rankdifferL, int rankdifferR,AVLNode dad,int count) {
	  count+=5; //two rotate , one promote, two demote
	  if( (dad.getrankdifferL()==0 && dad.getrankdifferR()==2) &&  //left son is the problematic one case3
		((AVLNode)dad.getLeft()).getrankdifferL()==2 && ((AVLNode)dad.getLeft()).getrankdifferR()==1)  
		  {
		  this.DoubleRotate((AVLNode)dad.getLeft().getRight(),(AVLNode)dad.getLeft(),dad, "lr"); //rotate left and then rotate right
		  this.update_all((AVLNode)dad);
		  this.update_all((AVLNode)dad.getParent().getLeft());
		  this.update_all((AVLNode)dad.getParent());
		  } //fix size from prevdad.getparent() to the root
	  else if(((dad.getrankdifferL()==2 && dad.getrankdifferR()==0) &&  //right son is the problematic one case3
			  ((AVLNode)dad.getRight()).getrankdifferL()==1 && ((AVLNode)dad.getRight()).getrankdifferR()==2))
	  { //case3
		  this.DoubleRotate((AVLNode)dad.getRight().getLeft(),(AVLNode)dad.getRight(),dad, "rl"); //rotate right and then rotate left
		  this.update_all((AVLNode)dad);
		  this.update_all((AVLNode)dad.getParent().getRight());
		  this.update_all((AVLNode)dad.getParent());
		  //fix size from prevdad.getparent() to the root  
	  }
	  return count;}

  /**
   * public int insert(int k, String i)
   *
   * inserts an item with key k and info i to the AVL tree.
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
   * promotion/rotation - counted as one rebalnce operation, double-rotation is counted as 2.
   * returns -1 if an item with key k already exists in the tree.
   */
   public int insert(int k, String i) {
	   if (this.empty()) { 			// if tree is empty we will create new AVLTree
		   AVLNode newone=new AVLNode(k,i);
		   this.root=newone;
		   this.minkey=k;
		   this.mininfo=i;
		   this.maxkey=k;
		   this.maxinfo=i;
		   return 0; 			// create the tree and return 0 , no rebalance
	   }
	  AVLNode tempnode= this.Recsearch(k,this.root);  //tempnode is virtual leaf
	  if(tempnode.getKey()==k) { // if key exists, we dont change the info
		  return -1;
	  }
	  else {					// node is not in the tree
		  if (k<this.minkey) { //check update MIN
			  this.minkey=k;
			  this.mininfo=i;
			  }
		  if(k>this.maxkey) { //check update MAX
			  this.maxkey=k;
			  this.maxinfo=i;
			  }
		  int count=0;
		  
		  AVLNode newnode=new AVLNode(k,i);
		  AVLNode dad=(AVLNode)tempnode.getParent();
		  AVLNode point_son= new AVLNode(-1);
		  
		  newnode.setParent(dad);   //  newnode dad is tempnode dad
		  
		  if(dad.getLeft().getKey()!=-1 || dad.getRight().getKey()!=-1) { //parent is not a leaf
			  if(dad.getLeft().getKey()==-1) { // new node is left son
				  dad.setLeft(newnode);
			  }
			  else { dad.setRight(newnode);	 } // new node is right son 
			  this.update_size(dad);
			  this.update_rankdiffer(dad); // update rank differ , tree AVL is valid  
  
		  }
		  else { //parent is a leaf
			  if (dad.getKey()>k) { // new node is left boy
				  dad.setLeft(newnode); // newnode is right boy
			  }
			  else { dad.setRight(newnode);} // newnode is left boy
			  
			  this.update_rankdiffer(dad);
			  

			 
			  while( (dad!=null) && ((dad.getrankdifferL()==0 && dad.getrankdifferR()==1)||     //check if rank differ is invalid
					(dad.getrankdifferL()==1 && dad.getrankdifferR()==0)||
					(dad.getrankdifferL()==2 && dad.getrankdifferR()==0)||
					(dad.getrankdifferL()==0 && dad.getrankdifferR()==2)) ) {
				
				  count+=this.insert_rebalance(dad.getrankdifferL(),dad.getrankdifferR(),dad,0);
				  point_son = dad;
				  dad=(AVLNode) dad.getParent();
				  
			  } // close while
		  } // close parent is a leaf
		  
		  while((AVLNode)dad !=null && (AVLNode)dad.getParent() !=null) { //update size in the tree
			  this.update_size((AVLNode)dad.getParent());
			  point_son = dad;
			  dad=(AVLNode) dad.getParent();
		  }
		  if(dad!=null) {this.root=dad; 
		  update_size(dad);}
		  else {this.root=point_son;
		  update_size(point_son);}
		  return count;
		  }
	  
	 
   }
   
   private AVLNode findmin(AVLNode node) { //find the minimun in the tree
	   if(!this.empty()) {
	   while(node.getLeft().getKey()!=-1) {
		   node=(AVLNode)node.getLeft();
	   }
	   return node; 
   }
	   return null;}
   
   private AVLNode findmax(AVLNode node) { //find the maximum in the tree
	   if(!this.empty()) {
	   while(node.getRight().getKey()!=-1) {
		   node=(AVLNode)node.getRight();
	   }
	   return node; 
   }
	   return null; }
   
   private AVLNode successor(AVLNode node) { //find the successor for node
	   if(node.getRight().getKey()!=-1) {
		   return  this.findmin((AVLNode)node.getRight()); //to find the MIN in the sub tree
	   }
	   else {
		   AVLNode nodey=(AVLNode) node.getParent();
		   while(node.getParent() !=null && node.getKey()==nodey.getRight().getKey()) {
			   node= nodey;
			   nodey=(AVLNode) node.getParent();
		   }
		   return nodey;
	   }
   }
   
   private void the_deletion(AVLNode dad,AVLNode tempnode, char whatson) { //delete the node
	   if (whatson=='n') {                           // if we want to delete the root, only if root has one or zero sons
		   if (tempnode.getLeft().getKey()!=-1) {    // if has a left son
			   tempnode.getLeft().setParent(null);
			   this.root=(AVLNode) tempnode.getLeft();
			   tempnode.setLeft(null);
		   }
		   else if  (tempnode.getRight().getKey()!=-1){//  if has a right son
			   tempnode.getRight().setParent(null);
			   this.root=(AVLNode) tempnode.getRight();
			   tempnode.setRight(null); 
			   }
		   
	   else {                                         // root is a leaf, nullify the tree
		   this.root=null;
		   this.minkey=Integer.MAX_VALUE;
		   this.mininfo=null;
		   this.maxkey= Integer.MIN_VALUE;
		   this.maxinfo=null;
		   }}
	   
	   else {
		   if (whatson=='l') {          // if left son
			   if (tempnode.getLeft().getKey()!=-1) { //if has left grandson delete left son from dad, and give dad the left grandson son
				   dad.setLeft(tempnode.getLeft());                           
				   tempnode.getLeft().setParent(dad);
				   }
			   else  {                  // if has right grandson or a leaf,delete left son from dad, and give dad the right grandson son
				   dad.setLeft(tempnode.getRight());
				   tempnode.getRight().setParent(dad);  }
			   }
		   else if  (whatson=='r') {    // if right son
			   if (tempnode.getLeft().getKey()!=-1) { //if has left grandson delete left son from dad, and give dad the left grandson son
				   dad.setRight(tempnode.getLeft());                           
				   tempnode.getLeft().setParent(dad);
				   }
			   else  {                //if has right grandson delete left son from dad, and give dad the right grandson son
				   dad.setRight(tempnode.getRight());
				   tempnode.getRight().setParent(dad);
				   }
			   }
		this.update_all(dad);

		   }
	   }
   
   private int[] calculate_option(int rankdifferL, int rankdifferR, char whatson) { //calculate the rank differ for next step
	   int[] option= {0,0};
	   if((rankdifferL==2 && rankdifferR==1 && whatson=='r')||(rankdifferL==1 && rankdifferR==2 && whatson=='l')) 
	   {option[0]=2;option[1]=2;}                                 // after delete  rankdifferL and rankdifferR 2,2
	   else if((rankdifferL==2 && rankdifferR==1 && whatson=='l')||(rankdifferL==1 && rankdifferR==2 && whatson=='r'))
	   {option[0]=3;option[1]=1;}                                // after delete rankdifferL and rankdifferR 3,1
	   else if (whatson=='N') {
		   if (rankdifferL==2 && rankdifferR==2) 
		   {option[0]=2;option[1]=2; }
		   else if( (rankdifferL==3 && rankdifferR==1) || rankdifferL==1 && rankdifferR==3 ) 
		   {option[0]=3;option[1]=1; }
		   else { option[0]=0;option[1]=0; } // {0,0} means okay
	   }
	   return option;
	   }
   
  /**
   * public int delete(int k)
   *
   * deletes an item with key k from the binary tree, if it is there;
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
   * demotion/rotation - counted as one rebalnce operation, double-rotation is counted as 2.
   * returns -1 if an item with key k was not found in the tree.
   */


   public int delete(int k) {
	   if ( this.empty()) {
		   return -1;
	   }
	   AVLNode tempnode= this.Recsearch(k,this.root);
	   if(tempnode.getKey()==-1) {                             //not in the tree, return -1
		   return -1;
	   }
	   else {
		   int counter=0;
		   if(tempnode.getLeft().getKey()!=-1 && tempnode.getRight().getKey()!=-1) {   //if has two sons
			 AVLNode suc= this.successor(tempnode);       //replace the node we need to delete with his successor
			 tempnode.setKey(suc.getKey());
			 tempnode.setValue(suc.getValue());                     
			 tempnode=suc;                                //tempnode point to suc -now we want to delete suc
		   }                                              //now only case possible is a leaf or unary node
		   
		   AVLNode dad=(AVLNode) tempnode.getParent();    // a pointer to the dad of the node we want to delete
		   AVLNode point_son = new AVLNode(-1);           // a pointer to the dad future son
		   int[] option= {0,0};                // will help us recognize the state we are in after delete 
		   char whatson;                       // recognize if we are a left son or right
		   if(dad==null) {                     // we want to delete the root, only possible if root is unary or a leaf
			   whatson='n';
			   }
		   else {
			   if (dad.getLeft().getKey()==tempnode.getKey())    //if we are a left son
				   {whatson='l';}
			   else {whatson='r'; }
			   }
			   
		  if (dad!=null) {                                    //if the node is not the root, calculate the option
			  option=calculate_option(dad.getrankdifferL(), dad.getrankdifferR(), whatson);   
		  }
		   
		   // perform the node deletion !
		   
		   the_deletion(dad,tempnode,whatson); //the deletion
		   
		   while(dad!=null &&((option[0]==2 && option[1]==2) ||
				 (option[0]==3 && option[1]==1) )) {                      // while we are in a option that has a problem
			   
		   
			   if (option[0]==2 && option[1]==2) {                        //state 2,2 case 1
					this.update_all(dad);
				   this.update_rankdiffer((AVLNode) dad.getParent());
				   counter+=1;                                            //one demote
				   point_son=dad;
				   dad=(AVLNode) dad.getParent();
			   }
			   
			   else if (dad.getrankdifferL()==3 && dad.getrankdifferR()==1 && //  case2
				  ((AVLNode)dad.getRight()).getrankdifferL()==1 && 
				  ((AVLNode)dad.getRight()).getrankdifferR()==1) {            //  left son problematic, state 3,1 right_son 1,1 and break
				   
				   this.Rotate(dad,(AVLNode) dad.getRight(),'l');             //rotate left          
					this.update_all(dad);
					this.update_all((AVLNode) dad.getParent());
				   counter+=3;                                               //one rotate, one demote,one promote
				   point_son=(AVLNode) dad.getParent();
				   dad=(AVLNode) dad.getParent().getParent();                //new dad id dadPrev parent
				   break;
			   }
			   
			   else if (dad.getrankdifferR()==3 && dad.getrankdifferL()==1 && //case 2
				  ((AVLNode)dad.getLeft()).getrankdifferL()==1 &&             //simetrical- state 1,3 left_son 1,1 and break
				  ((AVLNode)dad.getLeft()).getrankdifferR()==1) {             //right son problematic
				   
				   this.Rotate(dad,(AVLNode) dad.getLeft(),'r');              //rotate right  
				   this.update_all(dad);
					this.update_all((AVLNode) dad.getParent());
					
				  
				   counter+=3;                                              //one rotate, one demote,one promote
				   point_son=(AVLNode) dad.getParent();
				   dad=(AVLNode) dad.getParent().getParent();                //new dad id dadPrev parent
				   break;
			   }

			   else if (dad.getrankdifferL()==3 && dad.getrankdifferR()==1 &&
					   ((AVLNode)dad.getRight()).getrankdifferL()==2 &&            //case 3
					   ((AVLNode)dad.getRight()).getrankdifferR()==1 )   {         // left son problematic, state 3,1 right_son 2,1
				   
				   this.Rotate(dad,(AVLNode) dad.getRight(),'l');                  //rotate left
				   this.update_all(dad);
				   this.update_size((AVLNode) dad.getParent());
				   this.update_rankdiffer((AVLNode)dad.getParent());
				   this.update_rankdiffer((AVLNode)dad.getParent().getParent());
				   counter+=3;                                                    //one rotate , two demote
				   point_son=(AVLNode) dad.getParent();
				   dad=(AVLNode) dad.getParent().getParent();
			   }
			   
			   else if (dad.getrankdifferR()==3 && dad.getrankdifferL()==1 &&  //case 3
					   ((AVLNode)dad.getLeft()).getrankdifferL()==1 &&        // right son problematic, simetrical- state 1,3 left_son 1,2
					   ((AVLNode)dad.getLeft()).getrankdifferR()==2) {
				   this.Rotate(dad,(AVLNode) ((AVLNode)dad).getLeft(),'r');   //rotate right
				   this.update_all(dad);
				   this.update_size((AVLNode) dad.getParent());
				   this.update_rankdiffer((AVLNode)dad.getParent());
				   this.update_rankdiffer((AVLNode)dad.getParent().getParent());
				   counter+=3;
				   point_son=(AVLNode) dad.getParent();
				   dad=(AVLNode) dad.getParent().getParent();
			   }
			   
			   else if (dad.getrankdifferL()==3 && dad.getrankdifferR()==1 &&      //case 4
					  ((AVLNode)dad.getRight()).getrankdifferR()==2 &&             // left son problematic 
					  ((AVLNode)dad.getRight()).getrankdifferL()==1) {             // state 3,1 right_son 1,2
				   this.DoubleRotate((AVLNode)dad.getRight().getLeft(),(AVLNode) dad.getRight(), dad  ,"rl");
				   this.update_all(dad);
				   this.update_all((AVLNode) dad.getParent().getRight());
				   this.update_all((AVLNode) dad.getParent());
				   this.update_rankdiffer((AVLNode) dad.getParent().getParent());
				   counter+=6;                                                     // 2 rotate,3 demote, 1 promote
				   point_son=(AVLNode) dad.getParent();
				   dad=(AVLNode) dad.getParent().getParent();
			   }
			   else if (dad.getrankdifferL()==1 && dad.getrankdifferR()==3 &&      //case 4
					  ((AVLNode)dad.getLeft()).getrankdifferL()==2 && 
					  ((AVLNode)dad.getLeft()).getrankdifferR()==1) {              // state 1,3 left_son 2,1
				   this.DoubleRotate((AVLNode) dad.getLeft().getRight(),(AVLNode)dad.getLeft(), dad  ,"lr");
				   this.update_all(dad);
				   this.update_all((AVLNode) dad.getParent().getLeft());
				   this.update_all((AVLNode) dad.getParent());
				   this.update_rankdiffer((AVLNode) dad.getParent().getParent());
				   counter+=6;                                                    // 2 rotate,3 demote, 1 promote
				   point_son=(AVLNode) dad.getParent();
				   dad=(AVLNode) dad.getParent().getParent();
			   }
			   
			                                                  // recognize the state we are in-after the last fix
			   if (dad!=null) {
				   option=calculate_option(dad.getrankdifferL(), dad.getrankdifferR(), 'N');
			   }
		   
		   }  //close while
		   
		   //now in a good option!! means-tree is fixed
		   
		   	while((dad!=null) && (AVLNode)dad.getParent()!=null) {         // while not  root, update size
				  update_size((AVLNode)dad.getParent());
				  point_son = dad;
				  dad=(AVLNode) dad.getParent(); 
				  }
		   	if (whatson!='n') {                                           //in case we did not delete the root,
		   		if(dad!=null) {this.root=dad; dad.setParent(null);}       //update the root
		   		else {this.root=point_son; point_son.setParent(null);}
		   		}
		   
		   
		   	if (k==this.minkey) {           //if we have deleted the minimum; need to re calculate it
		   		if(!this.empty()) {         //if not empty , calculate new min
		   			AVLNode temp=this.findmin(this.root);
					minkey=temp.getKey();   //update MIN
		   			mininfo=temp.getValue();
			   }
			   else {                       // tree is empty
				   this.minkey=Integer.MAX_VALUE;
				   this.mininfo=null;  }
		   } 
		   	
		   if (k==this.maxkey) {         //if we have deleted the maximum; need to re calculate it
			   if(!this.empty()) {       //if not empty , calculate new max
			   AVLNode temp=this.findmax(this.root);			   
			   maxkey=temp.getKey();     //update MAX
			   maxinfo=temp.getValue();
		   }
			   else {                   // tree is empty
			   this.maxkey= Integer.MIN_VALUE;
				this.maxinfo=null; }
		   }
		   
		   return counter;
		   } //close else, means the node was in the tree, got deleted and the tree is fixed
	   }
		   
	   

   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty
    */
   public String min()
   {
	   return this.mininfo; 
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty
    */
   public String max()
   {
	   return this.maxinfo; 
   }


private AVLNode[] IOTree(AVLNode node) { //Returns a sorted array which contains all nodes in the tree,
	  if (!this.empty()) {
		  AVLNode[] arrnode= new AVLNode[this.root.getsize()];
		  RecIOTree(node,arrnode,0);
		  return arrnode;
	  }
	  return null;
  }

private void RecIOTree( AVLNode node, AVLNode[] arrnode, int index) {//the rec-sorting op
	if (node!= null && node.getKey()!=-1 && index< this.root.getsize()) {
		RecIOTree((AVLNode)node.getLeft(), arrnode, index);
		arrnode[index+((AVLNode) node.getLeft()).getsize()]=node;
		RecIOTree((AVLNode)node.getRight(), arrnode, index+1+((AVLNode)node.getLeft()).getsize());
		}
	}

/**
 * public int[] keysToArray()
 *
 * Returns a sorted array which contains all keys in the tree,
 * or an empty array if the tree is empty.
 */
 public int[] keysToArray()
  {
	 if (!this.empty()) {
	 AVLNode[] temparr=IOTree(this.root); 
     int[] nodeKey= new int[this.root.getsize()];
     for (int i=0 ; i<nodeKey.length; i++) {
     	nodeKey[i]=(temparr[i]).getKey();
     }
     return nodeKey;   
  }
	 return null;}

  /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */
  public String[] infoToArray()
  {
		if (!this.empty()) {

	  AVLNode[] temparr=IOTree(this.root); 
      String[] nodeinfo= new String[this.root.getsize()];
      for (int i=0 ; i<nodeinfo.length; i++) {
      	nodeinfo[i]=temparr[i].getValue();
      }
      return nodeinfo;   
  }
		return null;}

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    *
    * precondition: none
    * postcondition: none
    */
   public int size()
   {
	   if(!this.empty()) {
	   return this.root.getsize(); 
   }
	   else {return 0;}
   }
   
     /**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    *
    * precondition: none
    * postcondition: none
    */
   public IAVLNode getRoot()
   {
	   return this.root;
   }
     /**
    * public string split(int x)
    *
    * splits the tree into 2 trees according to the key x. 
    * Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
	  * precondition: search(x) != null (i.e. you can also assume that the tree is not empty)
    * postcondition: none
    */   
   public AVLTree[] split(int x)
   {
	   this.insert(x, "");
	   AVLNode node_split=Recsearch(x,this.root);   // insert and get a pointer to node x
	   AVLTree left= new AVLTree();            // create new tree for keys<x 
	   AVLTree right= new AVLTree();           // create new tree for keys>x
	   if (node_split.getLeft().getKey()!=-1) {       // if x has left son - this is left root
		   left.root=(AVLNode) node_split.getLeft();  
		   node_split.getLeft().setParent(null);
	   }
	   if (node_split.getRight().getKey()!=-1) {     // if x has right son - this is right root
		   right.root=(AVLNode) node_split.getRight();  
		   node_split.getRight().setParent(null);
	   }
	   
	   
	   while (node_split.getParent()!=null) {

		   if (node_split.getParent().getRight().getKey()==node_split.getKey()) { // node_split is a right son
			  AVLTree templeft=new AVLTree();
			  if(node_split.getParent().getLeft().getKey()!=-1) {
			  templeft.root=(AVLNode) node_split.getParent().getLeft();
			  node_split.getParent().getLeft().setParent(null);  

			  AVLNode temp_nodesplit_parent = new AVLNode(node_split.getParent().getKey(),node_split.getParent().getValue());
			  			  
			  left.join(temp_nodesplit_parent, templeft); } // join left, node_splite parent, templeft
			  
			  else {left.insert(node_split.getParent().getKey(),node_split.getParent().getValue() ); }
			  
		   }
		   
		   else {// (node_split.getParent().getLeft().getKey()==node_split.getKey()) { // node_split is a left son
				  AVLTree tempright=new AVLTree();
				  if(node_split.getParent().getRight().getKey()!=-1) {
				  tempright.root=(AVLNode) node_split.getParent().getRight();
				  node_split.getParent().getRight().setParent(null);   
				  
				  AVLNode temp_nodesplit_parent = new AVLNode(node_split.getParent().getKey(),node_split.getParent().getValue());
				  
				  right.join(temp_nodesplit_parent, tempright);}       // join right, node_splite parent, tempright
				  else {right.insert(node_split.getParent().getKey(),node_split.getParent().getValue() ); }
			   }
		   
		   
			  node_split=(AVLNode) node_split.getParent(); 

	   }                 // close while
	   
	   
	   
		if(!left.empty()) {  //update min and max for left tree
	   AVLNode minleft=left.findmin((AVLNode) left.getRoot());
	   AVLNode maxleft=left.findmax((AVLNode) left.getRoot());
	   left.minkey=minleft.getKey(); left.mininfo=minleft.getValue();
	   left.maxkey=maxleft.getKey(); left.maxinfo=maxleft.getValue();
		}
		else {left.minkey=Integer.MAX_VALUE; left.mininfo=null;
		   left.maxkey=Integer.MIN_VALUE; left.maxinfo=null;}
		
		if(!right.empty()) { //update min and max for right tree
	   AVLNode minright=right.findmin((AVLNode) right.getRoot());
	   AVLNode maxright=right.findmax((AVLNode) right.getRoot());
	   right.minkey=minright.getKey(); right.mininfo=minright.getValue();
	   right.maxkey=maxright.getKey(); right.maxinfo=maxright.getValue(); }
		
		else {right.minkey=Integer.MAX_VALUE; right.mininfo=null;
		right.maxkey=Integer.MIN_VALUE; right.maxinfo=null;}
		
		
	   AVLTree[] res= {left,right};
	   return res;
   }
   
   private void the_join(IAVLNode x,AVLNode small, AVLNode big, char dir) { //the join op
	   if (dir=='l'){
	   x.setRight(big);
	   big.getParent().setLeft(x);
	   x.setParent(big.getParent());
	   big.setParent(x);
	   }
	   else {x.setLeft(big);
	   big.getParent().setRight(x);
	   x.setParent(big.getParent());
	   big.setParent(x);	   
	   }
   }

   /**
    * public join(IAVLNode x, AVLTree t)
    *
    * joins t and x with the tree. 	
    * Returns the complexity of the operation (|tree.rank - t.rank| + 1).
	* precondition: keys(x,t) < keys() or keys(x,t) > keys(). t/tree might be empty (rank = -1).
    * postcondition: none
    */   
   

   
   public int join(IAVLNode x, AVLTree t) {
	   if( (t.empty()) && (this.empty())){               // in case t and this is empty
		   this.insert(x.getKey(),x.getValue());        // insert x to the tree
		   return 1;} 
	   
	   if(t.empty()) {
		   int res=this.getRoot().getHeight();
		   this.insert(x.getKey(),x.getValue()); 
		   return res+2;
	   }
	  
	   if(this.empty()) {// this is empty 
		   int res=t.getRoot().getHeight();
		   t.insert(x.getKey(), x.getValue());
			   this.root=(AVLNode) t.getRoot();
			   this.minkey=t.minkey; this.mininfo=t.mininfo;
			   this.maxkey=t.maxkey; this.maxinfo=t.maxinfo;
			   return res+2; 
		   }
	   
	   
	   int res = Math.abs(this.getRoot().getHeight() - t.getRoot().getHeight())+1;
	   int bigMin;
	   AVLNode big;      // a point to the root of the node with the bigger rank
	   AVLNode small;    // a point to the root of the node with the smaller rank
	   char dir;         // when we join, where to go in big
	   
	   if(this.getRoot().getHeight()<t.getRoot().getHeight()) {    // rank(this)<=rank(t) t has the bigger rank   
		   big = (AVLNode) t.getRoot();                            // t is "big" ; this.tree is "small"
		   bigMin=t.minkey;
		   small = (AVLNode) this.getRoot();
		   }
	  else if (this.getRoot().getHeight()>t.getRoot().getHeight()){ // rank(this)>rank(t) this has the bigger rank                         
		   big = (AVLNode) this.getRoot();                         // this.tree is "big" ; t is "small" 
		   bigMin=this.minkey;
		   small = (AVLNode) t.getRoot();
	   }
	   
	  else {   // rank(this)==rank(t) , x is the new root!
		  if(x.getKey()<this.getRoot().getKey()) {  // tree is right son; t is left son
			  x.setRight(this.getRoot());
			  this.getRoot().setParent(x);
			  x.setLeft(t.getRoot());
			  t.getRoot().setParent(x);
			  this.minkey=t.minkey;
			  this.mininfo=t.mininfo;
		  }
		  else {  // tree is left son; t is right son
			  x.setRight(t.getRoot());
			  t.getRoot().setParent(x);
			  x.setLeft(this.getRoot());
			  this.getRoot().setParent(x);
			  this.maxkey=t.maxkey;
			  this.maxinfo=t.maxinfo;
		  }
		  this.root=(AVLNode) x;
		  this.update_all(this.root);
		  return 1;
	  }
	   // find the direction for the join
	   if (x.getKey()<bigMin) {   // now (small.Max-key)<x<(big.Min-key) ; means the direction is Left 
		   dir='l'; }
	   
	   else {
		   dir='r';  }          // now (big.Max-key)<x<(smaill.Min-key) ; means the direction is Right 
	    
	   
	   //find the node we will do the join
	   while (big.getHeight() > small.getHeight()) { 
		   if (dir=='l') {
			   big=(AVLNode) big.getLeft(); }
		   else {
			   big=(AVLNode) big.getRight(); }
	   }
	   
	   //the join
	   if (dir=='l') { 
		   if(small.getKey()==t.getRoot().getKey()) {
			   x.setLeft(t.getRoot());
			   t.getRoot().setParent(x);
			   the_join(x,small,big,dir);
			   }
		   else { //small==this.root
			   x.setLeft(this.getRoot());
			   this.getRoot().setParent(x);
			   the_join(x,small,big,dir);
			 	}
		   }
	   
	   else { //dir = r
		   if(small.getKey()==t.getRoot().getKey()) {
			   x.setRight(t.getRoot());
			   t.getRoot().setParent(x);
			   the_join(x,small,big,dir);
			   }
		   else { //small==this.root
			   x.setRight(this.getRoot());
			   this.getRoot().setParent(x);
			   the_join(x,small,big,dir);
			   }
		   }
	   
	   
	  AVLNode point_son=new AVLNode(-1);
	  this.update_all((AVLNode) x);
	  update_rankdiffer((AVLNode) x.getParent());
	  big=(AVLNode) x.getParent();
	  int cnt_rebalance=0; //counting rebalance op
	   
	  
	   while( (big!=null) &&   //check if rank differ is invalid , big is c in class
			  ( (big.getrankdifferL()==0 && big.getrankdifferR()==1)||                  
				(big.getrankdifferL()==1 && big.getrankdifferR()==0)||
				(big.getrankdifferL()==2 && big.getrankdifferR()==0)||
				(big.getrankdifferL()==0 && big.getrankdifferR()==2)) ){
			  
		   cnt_rebalance+=this.insert_rebalance(big.getrankdifferL(),big.getrankdifferR(),big,cnt_rebalance);
		   point_son = big;
		   big=(AVLNode) big.getParent();
	   }	// close while tree is balance  
	   
	   while((AVLNode)big !=null && (AVLNode)big.getParent() !=null) { //update size in the tree
			  this.update_size((AVLNode)big.getParent());
			  point_son = big;
			  big=(AVLNode) big.getParent();
		  }
	   
	   //update root
		  if(big!=null) {this.root=big;
		  update_size(big);}
		  else {this.root=point_son;
		  update_size(point_son);}
	  	   
	  //update min and max
	   if (this.maxkey<x.getKey()) { //maxkey()<x.key<t.minkey, update max
		   this.maxkey=t.maxkey; this.maxinfo=t.maxinfo;
	   }
	   else { this.minkey=t.minkey; this.mininfo=t.mininfo;	   } //update min
	   
	   return res;
	   }

	/**
	   * public interface IAVLNode
	   * ! Do not delete or modify this - otherwise all tests will fail !
	   */
	public interface IAVLNode{	
		public int getKey(); //returns node's key (for virtuval node return -1)
		public String getValue(); //returns node's value [info] (for virtuval node return null)
		public void setLeft(IAVLNode node); //sets left child
		public IAVLNode getLeft(); //returns left child (if there is no left child return null)
		public void setRight(IAVLNode node); //sets right child
		public IAVLNode getRight(); //returns right child (if there is no right child return null)
		public void setParent(IAVLNode node); //sets parent
		public IAVLNode getParent(); //returns the parent (if there is no parent return null)
		public boolean isRealNode(); // Returns True if this is a non-virtual AVL node
    	public void setHeight(int height); // sets the height of the node
    	public int getHeight(); // Returns the height of the node (-1 for virtual nodes)         

	}

   /**
   * public class AVLNode
   *
   * If you wish to implement classes other than AVLTree
   * (for example AVLNode), do it in this file, not in 
   * another file.
   * This class can and must be modified.
   * (It must implement IAVLNode)
   */
  public class AVLNode implements IAVLNode{
	  private int key;
	  private String info;
	  private AVLNode rigthboy;
	  private AVLNode leftboy;
	  private AVLNode dad;
	  private int height;
	  private int rankdifferL;
	  private int rankdifferR;
	  private int size;
	  
	  public AVLNode(int key, String info) {
		  this.key=key;
		  this.info=info;
		  this.size=1;
		  this.height=0;
		  this.rigthboy= new AVLNode(-1);
		  this.rigthboy.setParent(this);
		  this.leftboy= new AVLNode(-1);
		  this.leftboy.setParent(this);
		  this.rankdifferL=1; //rank difference with left virtual leaf is 1
		  this.rankdifferR=1; //rank difference with right virtual leaf is 1
	  }
	  
	  public AVLNode(int key) {    // constructor for virtual leaf
		  this.key=key;
		  this.height=-1;
		  this.size=0;
	  }
	  
		public int getKey()
		{
			return this.key; 
		}
	
		public void setKey(int k){
			this.key=k; 
		}
		public String getValue()
		{
			return this.info; 
		}
		public void setValue(String value){
			this.info=value; 
		}
		public void setLeft(IAVLNode node)
		{
			this.leftboy=(AVLNode)node; 
		}
		public IAVLNode getLeft()
		{
			return this.leftboy; 
		}
		public void setRight(IAVLNode node)
		{
			this.rigthboy=(AVLNode)node; 
		}
		public IAVLNode getRight()
		{
			return this.rigthboy; 
		}
		public void setParent(IAVLNode node)
		{
			this.dad=(AVLNode)node;
		}
		public IAVLNode getParent()
		{
			return this.dad; 
		}
		// Returns True if this is a non-virtual AVL node
		public boolean isRealNode()
		{
			return (this.key!=-1); //  because key virtual leaf is -1
		}
    public void setHeight(int height)
    {
      this.height=height; 
    }
    public int getHeight()
    {
      return this.height; 
    }
    private void setsize(int size) {
    	this.size=size;
    }
    private int getsize() {
    	return this.size;
    }
    private void setrankdifferL(int rank) {
    	this.rankdifferL=rank;
    }
    private int getrankdifferL() {
    	return this.rankdifferL;
    }
    private void setrankdifferR(int rank) {
    	this.rankdifferR=rank;
    }
    private int getrankdifferR() {
    	return this.rankdifferR;
    }

  }
  

}

