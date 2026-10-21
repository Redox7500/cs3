package io.github.redox7500.project2;

import java.util.BitSet;

public class Project2TowersOfHanoi
{
    private static final int DEFAULT_HEIGHT = 5;
    private static final int DEFAULT_PEGS   = 3;

    private final int[][] towers;
    private final int[] towerHeights;
    private final int[][] diskLocations;

    public Project2TowersOfHanoi(int height, int pegs)
    {
        this.towers = new int[pegs][height];
        this.diskLocations = new int[height][2];

        for (int i = 0; i < height; i++)
        {
            this.towers[0][i] = height - i;
            this.diskLocations[i] = new int[]{0, height};
        }

        this.towerHeights = new int[pegs];
        this.towerHeights[0] = height;
    }

    public Project2TowersOfHanoi(int[][] towers, boolean verify) // i need to make it work for when disks are missing don't i, but honestly you're weird if you do that
    {
        int towerCount = towers.length;
        if (verify) assert towerCount > 0 : "0 towers";

        int firstTowerHeight = towers[0].length;
        if (verify) assert firstTowerHeight > 0 : "First tower has no height";

        this.towers = towers;

        this.diskLocations = new int[firstTowerHeight][2];
        for (int i = 0; i < firstTowerHeight; i++) this.diskLocations[i] = new int[]{-1, -1};

        this.towerHeights = new int[towerCount];

        for (int i = 0; i < towerCount; i++)
        {
            int towerHeight = towers[i].length;

            if (verify) assert towerHeight == towers[0].length : "Incorrectly sized tower; tower arrays must all have the same height";
            
            this.towerHeights[i] = towerHeight;

            int lastDiskSize = 0;
            for (int j = 0; j < towerHeight; j++)
            {
                int currentDiskSize = towers[i][j];

                if (verify)
                {
                    assert currentDiskSize > 0 && (j == 0 || currentDiskSize > lastDiskSize) : "Larger disk is on top of smaller disk";
                    assert this.diskLocations[currentDiskSize][0] == -1 && this.diskLocations[currentDiskSize][1] == -1 : "Duplicate disk"; // could remove the second condition i guess
                }

                this.diskLocations[currentDiskSize][0] = i;
                this.diskLocations[currentDiskSize][1] = j;
                // anything more i need to add? i'm not really sure but i don't think so
            }
        }
    }

    public Project2TowersOfHanoi() {this(Project2TowersOfHanoi.DEFAULT_HEIGHT, Project2TowersOfHanoi.DEFAULT_PEGS);}

    public Project2TowersOfHanoi(int height) {this(height, Project2TowersOfHanoi.DEFAULT_PEGS);}

    public Project2TowersOfHanoi(int[][] towers) {this(towers, true);}

    // private int height() {return this.towers[0].length;} // could do this i suppose

    public void moveTopDisk(int startTower, int endTower)
    {
        assert startTower >= 0 && startTower < this.towers.length : "Starting tower index out of range";
        assert endTower   >= 0 && endTower   < this.towers.length : "Ending tower index out of range";
        if (this.towerHeights[endTower] > 0) assert this.towers[startTower][this.towerHeights[startTower] - 1] < this.towers[endTower][this.towerHeights[endTower] - 1] : "Disk cannot be placed here";


        int disk = this.towers[startTower][--this.towerHeights[startTower]];
        this.towers[startTower][this.towerHeights[startTower]] = 0;

        this.diskLocations[disk][0] = endTower;
        this.diskLocations[disk][1] = this.towerHeights[endTower];

        this.towers[endTower][this.towerHeights[endTower]++] = disk;
    }

    public void moveDisk(int diskSize, int targetTower)
    {
        int[] diskLocation = this.diskLocations[diskSize];

        int startTower = diskLocation[0];

        if (startTower == targetTower) return;

        // fancy bs
        BitSet availableTowers = new BitSet(3);
        availableTowers.set(0);
        availableTowers.set(1);
        availableTowers.set(2);

        BitSet usedTowers = new BitSet(3);
        usedTowers.set(startTower);
        usedTowers.set(targetTower);

        availableTowers.andNot(usedTowers);

        int scratchTower = availableTowers.nextSetBit(0); // lowk i could just pass this in as an arg but idk this looks cool

        // for (int i = diskLocation[1] + 1; i < this.towers[0].length; i++)
        // {
        //     if (this.towers[diskLocation[0]][i] != 0) break;
        // }

        // if (this.towerHeights[diskLocation[0]] - 1 == diskLocation[1])

        // int[] disksAbove = new int[this.towerHeights[diskLocation[0]] - diskLocation[1] - 1];
        // System.arraycopy(this.towers[diskLocation[0]], diskLocation[1] + 1, disksAbove, 0, disksAbove.length);
        // for (int diskAbove : disksAbove)
        // {

        // }
        // System.out.println(diskLocation[1]);
        if (this.towerHeights[startTower] - 1 == diskLocation[1])
        {
            this.moveTopDisk(startTower, scratchTower);
        }
        else
        {
            for (int i = diskLocation[1] + 1; i < this.towerHeights[startTower]; i++)
            {
                this.moveDisk(this.towers[startTower][i], scratchTower);
            }
        }
    }

    public void solve(int targetTower)
    {
        for (int i = this.diskLocations.length - 1; i >= 0; i--) this.moveDisk(i, targetTower);
    }

    @Override
    public String toString()
    {
        StringBuilder toReturn = new StringBuilder();

        int maxDigits = (int)Math.log10(this.diskLocations.length) + 1;
        for (int i = 0; i < this.towers.length; i++)
        {
            for (int disk : this.towers[i])
            {
                toReturn.append(String.format("%" + maxDigits + "d ", disk));
            }
            if (i != this.towers.length - 1) toReturn.append("\n");
        }

        return toReturn.toString();
    }
}
