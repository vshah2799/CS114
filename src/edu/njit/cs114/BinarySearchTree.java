package edu.njit.cs114;

import java.util.*;

/**
 * Author: Ravi Varadarajan
 * Date created: 3/31/20
 */
public class BinarySearchTree<K extends Comparable<K>,V> {

    private BSTNode<K,V> root;
    private int size;

    public static class BSTNode<K extends Comparable<K>,V> implements BinTreeNode<K,V> {

        private K key;
        private V value;
        private int height;
        // number of keys (including the key in this node) in the subtree rooted at this node
        private int size;
        private BSTNode<K, V> left, right;

        public BSTNode(K key, V value, BSTNode<K, V> left, BSTNode<K, V> right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
            setHeight();
            setSize();
            /**
             * Complete code to store height and size (for homework)
             */
        }

        public void setHeight(){
            int leftHeight;
            int rightHeight;
            if(left == null){
                leftHeight = 0;
            }else{
                leftHeight = left.height;
            }

            if(right==null){
                rightHeight = 0;
            }else{
                rightHeight = right.height;
            }

            this.height = Math.max(leftHeight, rightHeight) + 1;
        }

        public void setSize(){
            int leftSize;
            int rightSize;
            if(left == null){
                leftSize = 0;
            }else{
                leftSize = left.size;
            }

            if(right==null){
                rightSize = 0;
            }else{
                rightSize = right.size;
            }

            this.size = 1 + leftSize + rightSize;

        }

        public BSTNode(K key, V value) {
            this(key, value, null, null);
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public BinTreeNode<K, V> leftChild() {
            return left;
        }

        @Override
        public BinTreeNode<K, V> rightChild() {
            return right;
        }

        @Override
        public boolean isLeaf() {
            return (left == null && right == null);
        }

        private void setLeftChild(BinTreeNode<K, V> node) {
            this.left = (BSTNode<K, V>) node;
        }

        private void setRightChild(BinTreeNode<K, V> node) {
            this.right = (BSTNode<K, V>) node;
        }

        private void setValue(V value) { this.value = value; };

        /**
         * Returns height of left subtree - height of right subtree
         * @return
         */
        @Override
        public int balanceFactor() {
            int leftHeight;
            int rightHeight;
            if(left == null){
                leftHeight = 0;
            }else{
                leftHeight = left.height;
            }

            if(right==null){
                rightHeight = 0;
            }else{
                rightHeight = right.height;
            }
            setSize();
            return leftHeight - rightHeight;


            /**
             * Complete code for the lab
             *
             */
        }

        private void copy(BSTNode<K,V> node) {
            this.key = node.key;
            this.value = value;
        }
    }


    public BSTNode<K,V> getRoot() {
        return root;
    }

    public BSTNode<K,V> insertAux(BSTNode<K,V> localRoot, K key, V value) {
        if (localRoot == null) {
            return new BSTNode<K,V>(key, value);
        }
        int result = key.compareTo(localRoot.key);
        if (result < 0) {
            localRoot.setLeftChild(insertAux(localRoot.left, key, value));
        } else if (result > 0){
            localRoot.setRightChild(insertAux(localRoot.right, key, value));
        } else {
            localRoot.setValue(value);
        }
        localRoot.setSize();
        localRoot.setHeight();


        /**
         * Complete code to set height of localRoot for the lab (also "size" for homework)
         */
        return localRoot;
    }



    /**
     * Insert/Replace (key,value) association or mapping in the tree
     * @param key
     * @param value value to insert or replace
     */
    public void insert(K key, V value) {
        this.root = insertAux(root, key, value);
    }

    // Extra credit problem
    private BSTNode<K,V> deleteAux(BSTNode<K,V> localRoot, BSTNode<K,V> parent, K key) {
        /**
         * complete for homework extra credit
         * you need to set height and size properly in the nodes of the subtrees affected
         */
        return null;
    }

    /**
     * Delete the value associaated with the key if it exists
     * @param key
     * @return value deleted if key exists else null
     */
    public V delete(K key) {
        BSTNode<K,V> node = deleteAux(root, null, key);
        return node == null ? null : node.getValue();
    }

    public int height() {
        return (root == null ? 0 : root.height);
    }

    public int size() {
        return (root == null ? 0 : root.size);
    }

    private boolean isBalanced(BSTNode<K,V> localRoot) {
        if (localRoot == null){
            return true;
        }
        int bf = localRoot.balanceFactor();
        if(bf < -1 || bf > 1){
            return false;
        }
        if(isBalanced(localRoot.left) && isBalanced(localRoot.right)){
            return true;
        }
        else
            return false;

        /**
         * Complete code here for the lab
         */
    }

    /**
     * Is the tree balanced ?
     * For every node, height of left and right subtrees differ by at most 1
     * @return
     */
    public boolean isBalanced() {
        return isBalanced(root);
    }

    /**
     * Get level ordering of nodes
     * @return a map which associates a level with list of nodes at that level
     */
    public Map<Integer, List<BSTNode<K,V>>> getNodeLevels() {
        Map<Integer, List<BSTNode<K,V>>> nodeLevels = new HashMap<>();
        if(this.root == null){ return nodeLevels;}

        ArrayList<BSTNode<K,V>> parentNodeList = new ArrayList<BSTNode<K,V>>();
        parentNodeList.add(this.root);
        int level = 0;
        nodeLevels.put(level,parentNodeList);
        while(!parentNodeList.isEmpty()){
            ArrayList<BSTNode<K, V>> childrenNodeList = new ArrayList<BSTNode<K, V>>();
            for(BSTNode<K,V> parentNode : parentNodeList) {
                if (parentNode.leftChild() != null) {
                    childrenNodeList.add(parentNode.left);
                }
                if (parentNode.rightChild() != null) {
                    childrenNodeList.add(parentNode.right);
                }
            }
            level += 1;
            if(!childrenNodeList.isEmpty()){
                nodeLevels.put(level,childrenNodeList);
            }
            parentNodeList = childrenNodeList;
        }

        /**
         * Complete code for the lab
         */
        return nodeLevels;
    }

    /**
     * Return list of nodes whose keys are greater than or equal to key1
     *   and smaller than or equal to key2
     * @param key1
     * @param key2
     * @return
     */
    public List<BSTNode<K,V>> getRange(K key1, K key2) {
        ArrayList<BSTNode<K,V>> list = new ArrayList<BSTNode<K,V>>();
        if(key1.compareTo(key2) > 0){
            return list;
        }

        return getRangeAux(root,key1,key2);
        /**
         * Complete code for homework (define a recursive aux function to be called from here)
         */
    }

    private List<BSTNode<K,V>> getRangeAux(BSTNode<K,V>localroot, K key1, K key2){
        ArrayList <BSTNode<K,V>> list = new ArrayList<BSTNode<K,V>>();
        if(localroot == null){
            return list;
        }
        if(key2.compareTo(localroot.key) <= 0){
            if(key1.compareTo(localroot.key) < 0){
                list.addAll(getRangeAux(localroot.left, key1, key2));
            }
            if(key2.compareTo(localroot.key) == 0){
                list.add(localroot);
            }
            return list;
        }
        else if(key2.compareTo(localroot.key) > 0){
            if(key1.compareTo(localroot.key) >= 0){
                if(key1.compareTo(localroot.key) == 0){
                    list.add(root);
                    }
                list.addAll(getRangeAux(localroot.right, key1, key2));
                }
            if(key1.compareTo(localroot.key) < 0){
                list.addAll(getRangeAux(localroot.left, key1, key2));
                list.add(localroot);
                list.addAll(getRangeAux(localroot.right, key1, key2));
            }
        }
        return list;
    }

    /**
     * Find number of keys smaller than or equal to the specified key
     * @param key
     * @return
     */

    private int rankAux(BSTNode<K,V> root, K key){
        int nKeys = 0;
        if(root == null){return 0;}
        if(key.compareTo(root.key) < 0){
            return rankAux(root.left, key);
        }
        else {
            if(root.left == null){
                nKeys = 0;
            }
            else{
                nKeys = root.left.size;
            }
            nKeys++;
            if(key.compareTo(root.key) > 0){
                nKeys = nKeys + rankAux(root.right, key);
            }
        }
        return nKeys;
    }

    public int rank(K key) {
        return rankAux(root,key);
        /**
         * Complete code for homework (define a recursive aux function to be called from here)
         */
    }


    public static void main(String [] args) {
        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();
        bst.insert(25,"a");
        bst.insert(10,"b");
        bst.insert(30,"c");
        bst.insert(5,"d");
        bst.insert(28,"e");
        bst.insert(35,"f");
        bst.insert(40,"g");
        bst.insert(8,"k");
        bst.insert(50,"l");
        System.out.println("Printing tree bst..");
        new BinTreeInOrderNavigator<Integer,String>().visit(bst.root);
        bst.insert(40,"m");
        System.out.println("Printing tree bst..");
        new BinTreeInOrderNavigator<Integer,String>().visit(bst.root);
        System.out.println("size of bst=" + bst.size());
        System.out.println("height of bst=" + bst.height());
        System.out.println("Is bst an AVL tree ? " + bst.isBalanced());
        Map<Integer, List<BSTNode<Integer,String>>> nodeLevels = bst.getNodeLevels();
        for (int level : nodeLevels.keySet()) {
            System.out.print("Keys at level " + level + " :");
            for (BSTNode<Integer,String> node : nodeLevels.get(level)) {
                System.out.print(" " + node.getKey());
            }
            System.out.println("");
        }
        BinarySearchTree<Integer, Integer> bst1 = new BinarySearchTree<>();
        bst1.insert(44,1);
        bst1.insert(17,2);
        bst1.insert(78,3);
        bst1.insert(50,4);
        bst1.insert(62,5);
        bst1.insert(88,6);
        bst1.insert(48,7);
        bst1.insert(32,8);
        System.out.println("Printing tree bst..");
        new BinTreeInOrderNavigator<Integer,Integer>().visit(bst1.root);
        System.out.println("size of bst1=" + bst1.size());
        System.out.println("height of bst1=" + bst1.height());
        System.out.println("Is bst1 an AVL tree ? " + bst1.isBalanced());
        Map<Integer, List<BSTNode<Integer,Integer>>> nodeLevels1 = bst1.getNodeLevels();
        for (int level : nodeLevels1.keySet()) {
            System.out.print("Keys at level " + level + " :");
            for (BSTNode<Integer,Integer> node : nodeLevels1.get(level)) {
                System.out.print(" " + node.getKey());
            }
            System.out.println("");
        }
        System.out.println("rank of key 10 in bst=" + bst.rank(10)); // should be 3
        System.out.println("rank of key 30 in bst=" + bst.rank(30)); // should be 6
        System.out.println("rank of key 3 in bst=" + bst.rank(3)); // should be 0
        System.out.println("rank of key 55 in bst=" + bst.rank(50)); // should be 9
        List<BSTNode<Integer,Integer>> rangeNodes = bst1.getRange(32,62);
        System.out.print("Keys in the range : [32,62] are:");
        // should get 32,44,48,50,62,
        for (BSTNode<Integer,Integer> node : rangeNodes) {
            System.out.print(node.key + ",");
        }
        System.out.println("");
       rangeNodes = bst1.getRange(10,50);
        System.out.print("Keys in the range : [10,50] are:");
        // should get 17,32,44,48,50,
        for (BSTNode<Integer,Integer> node : rangeNodes) {
            System.out.print(node.key + ",");
        }
        System.out.println("");
        rangeNodes = bst1.getRange(90,100);
        System.out.print("Keys in the range : [90,100] are:");
        // should get empty list
        for (BSTNode<Integer,Integer> node : rangeNodes) {
            System.out.print(node.key + ",");
        }
        System.out.println("");
    }

}
