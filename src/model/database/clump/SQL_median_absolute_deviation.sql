SELECT my_statistical_median(abs(clump_mass - (select my_statistical_median(clump_mass) from Clump))) FROM clump