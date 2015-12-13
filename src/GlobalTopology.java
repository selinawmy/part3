
public class GlobalTopology {
    int CLOCK; // The last time the routing table was updated
    // CostGraph is [0 1 2 3 4 5 6 7 8 9] related to the corresponding ID of path
    int[] CostGraph;
    //                                                                              To
    //                                             A             B          C          D       R1             R2          R4           R3        
    //                                         R1 Inf           Inf      Cost/Rx    Cost/Rx    Inf           Inf       Cost/R3      Cost/R4
    // RoutingTable is int[4][8][2]:  From     R2 Inf           Inf      Cost/Rx    Cost/Rx    Inf           Inf       Cost/R3      Cost/R4
    // All Cost is in (3D [0] )                  R4 Cost/Rx     Cost/Rx      Inf        Inf    Cost/R1        Cost/R2       Inf          Inf
    // Rx is the next router(3D[1])            R3 Cost/Rx     Cost/Rx      Inf        Inf    Cost/R1        Cost/R2       Inf          Inf
    // At this moment R1 R2 just received pkt from A or B, while R3 R4 from C or D, leading to the Infs
    // If the SRC is A or B and DES is A or B, R1 R2 will not receive and forward the pkts, leading to the Infs
    // It is meaningless for one router to send pkt to itself, leading to Infs
    int[][] RoutingTable;
}
