package elasticsearch;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.*;
import static org.elasticsearch.node.NodeBuilder.*;

public class ESClient {

	Node node;
	Client client;
	
	public ESClient() {
		
		node = nodeBuilder().clusterName("skynet").node();
		client = node.client();
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
