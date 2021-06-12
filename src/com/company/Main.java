package com.company;


import java.util.Random;

public class Main {

    public static int bossHealth = 1000;
    public static int bossDamage = 50;
    public static String bossDefenceType = " ";
    public static int[] heroesHealth = {250, 250, 250, 200, 400, 150, 170, 180};
    public static int[] heroesDamage = {20, 20, 20, 0, 20, 15, 10, 15};
    public static String[] heroesAttackType = {"Physical", "Magical", "Mental", "Medic", "Tank",
            "Lovka4", "Bercek", "Tor"};
    public static Random random = new Random();
    public static int takeDamage = (bossDamage / 5) * 2;


    public static void main(String[] args) {

        fightInfo();
        while (!isFinished()) {
            round();
        }

    }

    public static void round() {
        changeBossDefence();
        bossHit();
        tank();
        heroesHit();
        medic();
        lovka4();
        thor();
        bercek();
        fightInfo();
    }

    public static void medic() {
        int randomHero = random.nextInt(heroesAttackType.length);
        int medicHp = 50;

        if (heroesHealth[3] <= 0) {
            heroesHealth[3] = 0;
        } else if (heroesHealth[randomHero] > 0) {
            heroesHealth[randomHero] += medicHp;
            System.out.println(heroesAttackType[randomHero] + "+" + medicHp + "HP");
        }
    }

    /*public static int isAliveHeroes() {
        int aliveHeroes = 0;

        for (int i = 0; i < heroesDamage.length; i++) {
            if (4 == i) {
                continue;
            } else if (heroesHealth[i] > 0) {
                aliveHeroes += 1;
            }
        }
        return aliveHeroes;
    }*/

    public static void tank() {
        if (heroesHealth[4] < 0) {
            heroesHealth[4] = 0;
        } else {
            for (int i = 0; i < heroesAttackType.length; i++) {
                if (heroesHealth[i] < 0) {
                    heroesHealth[i] = 0;
                } else if (4 == i) {
                    continue;
                } else {
                    if (bossHealth > 0) {
                        heroesHealth[i] += takeDamage;
                        if (heroesHealth[4] - takeDamage < 0) {
                            heroesHealth[4] = 0;
                        } else {
                            heroesHealth[4] -= takeDamage;
                        }
                    }
                }
            }
            if (heroesHealth[4] > 0) {
                System.out.println("Tank take damage: " + (takeDamage * (heroesHealth.length - 1)));
            }
        }
    }

    public static void thor() {
        boolean coin = random.nextBoolean();

        if (heroesHealth[7] < 0) {
            heroesHealth[7] = 0;
        } else if (coin && bossHealth > 0) {
            for (int i = 0; i < heroesHealth.length; i++) {
                heroesHealth[i] += takeDamage + (bossDamage / 5);
            }
            System.out.println("Boss is stunned!");
        }
    }

    public static void lovka4() {
        boolean uklon = random.nextBoolean();

        if (heroesHealth[5] < 0) {
            heroesHealth[5] = 0;
        } else if (uklon && bossHealth > 0) {
            heroesHealth[5] += bossDamage - takeDamage;
            System.out.println("Lovkach uklon");
        }
    }

    public static void bercek() {
        int blok = 20;
        int uron = heroesDamage[6] + blok;
        if (heroesHealth[6] < 0) {
            heroesHealth[6] = 0;
        } else if (heroesHealth[4] > 0 && bossHealth - uron > 0) {
            heroesHealth[6] += blok;
            bossHealth -= uron;
            System.out.println("Berserk blocked " + blok + " , take " + (takeDamage - blok));
        } else if (bossHealth - uron > 0) {
            heroesHealth[6] += blok;
            bossHealth -= uron;
            System.out.println("Berserk blocked " + blok + " , take " + (bossDamage - blok));
        }

    }


    public static void changeBossDefence() {
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefenceType = heroesAttackType[randomIndex];
    }

    public static boolean isFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!");
            return true;
        }
        boolean dead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                dead = false;
                break;
            }
        }
        if (dead) {
            System.out.println("Boss win");
        }
        return dead;
    }

    public static void bossHit() {
        for (int i = 0; i < heroesHealth.length; i++) {

            if (heroesHealth[i] > 0 && bossHealth > 0) {

                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (bossDefenceType.equals(heroesAttackType[i])) {
                    int koeff = random.nextInt(9) + 2;
                    if (bossHealth - heroesDamage[i] * koeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * koeff;
                    }
                    System.out.println(heroesAttackType[i] + " critical hit " + heroesDamage[i] * koeff);
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {

                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    // Статистика боя
    public static void fightInfo() {
        System.out.println("_________________________");
        System.out.println("Boss health: " + bossHealth + " [" + bossDamage + "] \n");

        for (int i = 0; i < heroesAttackType.length; i++) {
            System.out.println("" +
                    heroesAttackType[i] + " health: " + heroesHealth[i] + " [" + heroesDamage[i] + "] ");
        }
        System.out.println("_________________________");
    }


}
