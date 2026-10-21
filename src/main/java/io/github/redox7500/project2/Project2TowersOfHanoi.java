package io.github.redox7500.project2;

public class Project2TowersOfHanoi
{
    private static final int DEFAULT_HEIGHT = 5;
    private static final int DEFAULT_PEGS   = 3;

    private final int[][] towers;
    private final int[] towerHeights;
    private final int[][] diskLocations;

    public Project2TowersOfHanoi() {this(Project2TowersOfHanoi.DEFAULT_HEIGHT, Project2TowersOfHanoi.DEFAULT_PEGS);}

    public Project2TowersOfHanoi(int height) {this(height, Project2TowersOfHanoi.DEFAULT_PEGS);}

    public Project2TowersOfHanoi(int height, int pegs)
    {
        this.towers = new int[pegs][height];
        this.diskLocations = new int[height][2];

        for (int i = 0; i < height; i++)
        {
            this.towers[0][i] = height - i;
            this.diskLocations[i] = new int[]{0, 0};
        }

        this.towerHeights = new int[pegs];
        this.towerHeights[0] = height;
    }

    public Project2TowersOfHanoi(int[][] towers)
    {
        int towerCount = towers.length;
        assert towerCount > 0 : "0 towers";

        int firstTowerHeight = towers[0].length;
        assert firstTowerHeight > 0 : "First tower has no height";

        this.towers = towers;

        this.diskLocations = new int[firstTowerHeight][2];
        for (int i = 0; i < firstTowerHeight; i++) this.diskLocations[i] = new int[]{-1, -1};

        this.towerHeights = new int[towerCount];

        for (int i = 0; i < towerCount; i++)
        {
            int towerHeight = towers[i].length;

            assert towerHeight == towers[0].length : "Incorrectly sized tower; tower arrays must all have the same height";
            
            this.towerHeights[i] = towerHeight;

            int lastDiskSize = 0;
            for (int j = 0; j < towerHeight; j++)
            {
                int currentDiskSize = towers[i][j];

                assert currentDiskSize > 0 && (j == 0 || currentDiskSize > lastDiskSize) : "Larger disk is on top of smaller disk";
                assert this.diskLocations[currentDiskSize][0] == -1 && this.diskLocations[currentDiskSize][1] == -1 : "Duplicate disk";

                // anything more i need to add? i'm not really sure but i don't think so
            }
        }
    }

    public void moveTopDisk(int startTower, int endTower)
    {
        assert startTower >= 0 && startTower < this.towers.length : "Starting tower index out of range";
        assert endTower   >= 0 && endTower   < this.towers.length : "Ending tower index out of range";

        int disk = this.towers[startTower][this.towerHeights[startTower]--];
        this.towers[endTower][this.towerHeights[endTower]++] = disk;
    }

    public void moveDisk(int diskSize, int targetTower)
    {
        int[] diskLocation = this.diskLocations[diskSize];

        for (int i = 0; i < diskLocation[1])
    }

    public void solve(int targetTower)
    {

    }

    private static value class A
    {
        
    }
}
