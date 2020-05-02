package Db;

import java.util.ArrayList;
import java.util.Arrays;

public class Compute {
    Pattern input;
    final int neuron = 25;
    int count = 0;

    public Compute(Pattern pattern) {
//        this.input = pattern;
        this.input = pattern;
    }

    //   비교 알고리즘 수행
    public int[] getResult() {
        DbManager manager = new DbManager();
        ArrayList<Pattern> patterns = manager.getPatterns();
        int[][] weight = setWeight(patterns);
        int[] out = new int[neuron];

        while (!check(patterns, out)) { // 모든 패턴이 일치할 때까지
//          새 출력 패턴을 구하고
            transfer(input.getP(), out, weight);
//          -1, 1의 값으로 변환
            hardLimiter(out);
//          입력 패턴에 새로 구해진 출력 패턴을 대입
            input.setP(out);
        }
//        result
        System.out.println("result");
        System.out.println(Arrays.toString(out));
        // 최종적으로 구해진 출력 패턴 반환
        return out;
    }

    // 학습 패턴에 수렴하는지 체크
    private boolean check(ArrayList<Pattern> patterns, int[] out) {
        boolean checked = false;
        // 각각의 패턴과 비교
        for (Pattern pattern : patterns) {
            for (int i = 0; i < neuron; i++) {
//              하나의 패턴과 모두 일치해야 참 반환
                if (pattern.getP()[i] == out[i]) {
                    checked = true;
                } else { // 하나라도 틀릴경우 안됨
                    checked = false;
                    break;
                }
            }
//            하나의 패턴과 일치하면 true 반환
            if (checked) {
                return true;
            }
        }
        return false;
    }

    //    가중치 배열 생성
    private int[][] setWeight(ArrayList<Pattern> patterns) {
        int[][] weight = new int[neuron][neuron];
        for (Pattern pattern : patterns) {
            for (int i = 0; i < neuron; i++) {
                for (int j = 0; j < neuron; j++) {
//                  자신과의 연결점은 0
                    if (i == j) {
                        weight[i][j] = 0;
                    } else { // 자신의 가로 세로 곱 계산 후 추가
                        weight[i][j] = weight[i][j] + (pattern.getP()[i] * pattern.getP()[j]);
                    }
                }
            }
        }
        return weight;
    }

    // 입력 패턴과 가중치를 곱해 새 출력패 생성
    private void transfer(int[] in, int[] out, int[][] weight) {
        for (int i = 0; i < neuron; i++) {
            int tmp = 0;
            for (int j = 0; j < neuron; j++) {
                tmp += in[j] * weight[i][j];
            }
            out[i] = tmp;
        }
    }

    // 배열을 모두 체크하여 0 이상이면 1, 아닐 겨우 -1로 변환
    private void hardLimiter(int[] out) {
        for (int i = 0; i < neuron; i++) {
            if (out[i] >= 0) {
                out[i] = 1;
            } else {
                out[i] = -1;
            }
        }
    }
}
