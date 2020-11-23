# ex1
######a project in my OOP course
Weighted graph is a set of nodes and edges whice each edge has a weight.  
this project build a weighted graph and nodes and apply algorithms on them.

#### node_info(Interface)
this interface represent a node in a weighted graph.
###### Variables
int key - represents node number  
double tag - represents edge value , also used for algorithms  
String info - used for algorithms.
###### Methodes
getKey() - Return the key (id) associated with this node.  
getInfo() - Returns this node info field.  
setInfo() - Sets info.  
getTag() - Return node tag.
setTag() - Sets tag of a node.
 

#### weighted_graph(Interface)

this interface represents a weighted graph fields and functions.

###### Variables
Hashmap V<Integer , node_info> - a hashmap of the nodes in the graph , gets node by key.  
Hashmap E<Integer , Hashmap <Integer , node_info> - Contains a Hashmap of neighbors with edge value.  
int Edge - edge size in the graph.  
int MC - number on changes in the graph.  
###### Methodes
getNode()-Returns node by key.  
hasEdge(int x,int y)- Returns a boolean if an edge exist or not.  
getEdge()-Returns a double of the edge weight.  
addNode()-add a new node to the graph with the given key.  

#### weighted_graph_algorithms(Interface)
this interface init a graph and make a set of algorithms on it also has save load option.  

#### Variables
Iterator NodeSearch - Iterator of node_info used in algorithms of this interface  
weighted_graph graph - the graph initialized by this interface.  
PriorityQueue Queue - Used by algorithms in this interface.  
