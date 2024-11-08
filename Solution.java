import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Solution {
    Random rand = new Random();
    public ArrayList<City> cities = new ArrayList<>();
    City startCity;

    public Solution(int[][] matrix, City startCity) {
        this.startCity = startCity;
        // Initialize cities and their connections
        for (int i = 0; i < matrix.length; i++) {
            City city = new City(false);
            cities.add(city);
            for (int j = 0; j < matrix[0].length; j++) {
                city.toCities.add(matrix[i][j]);  // Populate distances for each city
            }
        }
    }

    // Method to calculate the shortest path using brute-force
    public List<Integer> question1() {
        List<Integer> cityIndices = new ArrayList<>();
        for (int i = 0; i < cities.size(); i++) {
            if (i != cities.indexOf(startCity)) {  // Exclude the start city
                cityIndices.add(i);
            }
        }

        int minDistance = Integer.MAX_VALUE;
        List<Integer> bestPath = new ArrayList<>();

        // Generate all permutations of cities
        List<List<Integer>> permutations = permute(cityIndices);
        for (List<Integer> perm : permutations) {
            int totalDistance = 0;
            City currentCity = startCity;

            // Calculate the distance for this specific permutation
            for (int index : perm) {
                totalDistance += currentCity.toCities.get(index);
                currentCity = cities.get(index);
            }
            // Add distance back to the start city to complete the cycle
            totalDistance += currentCity.toCities.get(cities.indexOf(startCity));

            // Update the minimum distance if a shorter path is found
            if (totalDistance < minDistance) {
                minDistance = totalDistance;
                bestPath = new ArrayList<>(perm);
            }
        }
        return bestPath;
    }
    public String[] question2() {
        List<Integer> cityIndices = new ArrayList<>();
        for (int i = 0; i < cities.size(); i++) {
            if (i != cities.indexOf(startCity)) {  // Başlangıç şehri hariç tutuluyor
                cityIndices.add(i);
            }
        }

        List<List<Integer>> permutations = permute(cityIndices);
        String[] paths = new String[permutations.size()];

        int index = 0;
        for (List<Integer> perm : permutations) {
            int totalDistance = 0;
            City currentCity = startCity;
            StringBuilder path = new StringBuilder("Start -> ");

            // Her permütasyon için mesafeyi hesapla
            for (int cityIndex : perm) {
                totalDistance += currentCity.toCities.get(cityIndex);
                currentCity = cities.get(cityIndex);
                path.append("City ").append(cityIndex).append(" -> ");
            }

            // Başlangıç şehrine geri dönerek turu tamamla
            totalDistance += currentCity.toCities.get(cities.indexOf(startCity));
            path.append("Start");

            // Yol ve mesafe bilgisini `paths` dizisine ekle
            paths[index++] = path.toString() + " | Total Distance: " + totalDistance;
        }

        return paths;
    }

    public static List<Esya> question3(ArrayList<Esya> esyalar, int capacity) {
        int n = esyalar.size();
        int[][] dp = new int[n + 1][capacity + 1];
        
        for (int i = 1; i <= n; i++) {
            Esya esya = esyalar.get(i - 1);
            for (int w = 1; w <= capacity; w++) {
                if (esya.Agirlik <= w) {
                    dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - esya.Agirlik] + esya.Deger);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        // En yüksek değeri sağlayan eşyaları belirlemek için geri izleme
        List<Esya> result = new ArrayList<>();
        int w = capacity;
        for (int i = n; i > 0; i--) {
            // Eğer dp[i][w] önceki satırın değerinden farklı ise, bu eşyayı seçmişiz demektir
            if (dp[i][w] != dp[i - 1][w]) {
                Esya esya = esyalar.get(i - 1);
                result.add(esya);  // Eşyayı listeye ekle
                w -= esya.Agirlik; // Kapasiteyi güncelle
            }
        }

        return result;  // En yüksek değeri sağlayan eşyaların listesi
    }
    private List<List<Integer>> permute(List<Integer> nums) {
        List<List<Integer>> result = new ArrayList<>();
        permuteHelper(nums, new ArrayList<>(), result);
        return result;
    }

    private void permuteHelper(List<Integer> nums, List<Integer> path, List<List<Integer>> result) {
        if (nums.isEmpty()) {
            result.add(new ArrayList<>(path));  // Eğer tüm elemanlar eklendiyse sonucu kaydet
        } else {
            for (int i = 0; i < nums.size(); i++) {
                List<Integer> remaining = new ArrayList<>(nums);  // Kalan elemanları kopyala
                path.add(remaining.remove(i));  // Şimdiki elemanı 'path' yoluna ekle
                permuteHelper(remaining, path, result);  // Geriye kalanlarla permütasyon oluştur
                path.remove(path.size() - 1);  // En son eklenen elemanı kaldırarak bir sonraki döngüye hazırla
            }
        }
    }

}