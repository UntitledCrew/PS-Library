# 최소 신장 트리(MST)

> MST = Minimum Spanning Tree

## 사전 지식

### 그래프

- 노드와 간선으로 구성된 한정된 자료구조
- 방향이 있는(유향) / 없는(무향) 그래프로 나눈다.
- 사이클이 있기도 하고 없기도 하다.
- 간선에 가중치가 있을 수 있고, 가중 그래프 중 유향 그래프를 '네트워크'라 한다.



### 트리

- 그래프의 일종
- 사이클이 없고, 서로 다른 두 노드를 잇는 길이 하나 뿐인 그래프
- 최상위 노드를 루트 노드라 하며, 부모-자식 관계가 존재한다.
- 노드가 N개인 트리는 항상 N-1개의 간선을 갖는다.



## 신장 트리(Spanning Tree)

> 그래프 내의 모든 정점을 포함하는 트리

- 그래프 내의 모든 노드를 포함하되 사이클이 존재하지 않음.
- 하나의 그래프에 대해 여러 개의 신장 트리가 존재할 수 있다.



## 최소 신장 트리(Minimum Spanning Tree)

> Spanning Tree 중에서 사용된 간선들의 가중치 합이 최소인 트리

- 간선의 가중치 합이 최소여야 한다.

### Kruskal Algorithm

> 탐욕적인(Greedy) 방법을 이용하여 가중 그래프의 모든 정점을 최소 비용으로 연결하는 최적 경로를 구하는 알고리즘

#### 특징

- 각 단계에서 사이클을 이루지 않는 최소 비용 간선을 선택한다.
- 간선 선택을 기반으로 한다.
- 탐욕적인 방법은 순간에는 최적의 방법이지만, 전체를 봤을 때는 최적이라는 보장이 없기 때문에 검증을 해야 한다.
- 하지만 Kruskal Algorithm은 최적의 해답을 주는 것으로 증명되어 있다.



#### 방법

1. 그래프의 간선들을 가중치의 오름차순으로 정렬한다.
2. 정렬된 간선 리스트에서 순서대로 사이클을 형성하지 않는 간선을 선택한다. (가장 낮은 가중치의 간선을 선택)
   - 사이클을 확인하기 위해서 Union-Find 알고리즘을 사용한다.
3. 해당 간선을 현재의 MST(최소 비용 신장 트리)의 집합에 추가한다.



#### 구현(python)

```python
def kruskal(N, edge_list):
    def find(n):
        if parent[n] < 0:
            return n
        parent[n] = find(parent[n])
        return parent[n]

    def union(a, b):
        parent_a = find(a)
        parent_b = find(b)

        if parent_a == parent_b:
            return False

        if parent[parent_a] < parent[parent_b]:
            parent[parent_b] = parent_a
        elif parent[parent_a] > parent[parent_b]:
            parent[parent_a] = parent_b
        else:
            parent[parent_a] -= 1
            parent[parent_b] = parent_a
        return True

    minimum_weight = 0
    parent = [-1] * N
    result = []
    # 가중치 기준 오름차순 정렬 후 순회
    edge_list = sorted(edge_list, key=lambda weight: weight[2])
    for edge_data in edge_list:
        n1, n2, w = edge_data
        # 동일 집합인지 검사 + 다른 집합이면 집합 합치기
        if union(n1, n2):
            result.append(edge_data)
            minimum_weight += w

    return result, minimum_weight


print(kruskal(6, [[0, 1, 5], [0, 3, 2], [0, 4, 3], [1, 4, 1], [3, 4, 10], [1, 2, 2], [2, 5, 3], [4, 5, 4]]))
```



#### 시간 복잡도

- O(eloge) : e는 간선(edge) 개수
- 그래프 내에 적은 숫자의 간선만을 가지는 '희소 그래프'의 경우 사용



### Prim Algorithm

> 시작 정점에서부터 출발하여 신장트리 집합을 단계적으로 확장해나가는 방법

#### 특징

- 각 단계에서 사이클을 이루지 않는 최소 비용 간선을 선택한다.
- 정점 선택을 기반으로 한다.



#### 방법

1. 시작 단계에서는 시작 정점만이 MST(최소 신장 트리) 집합에 포함된다.
2. 앞 단계에서 만들어진 MST 집합에 인접한 정점들 중에서 가중치가 가장 낮은 간선으로 연결된 정점을 선택하여 트리를 확장한다.
3. 위 과정을 트리가 (N - 1)개의 간선을 가질 때까지 반복한다.



#### 구현(python)



#### 시간 복잡도

- O(N^2)
- 그래프에 간선이 많이 존재하는 '밀집 그래프'의 경우 사용



## 참고

[사전지식](https://velog.io/@gouz7514/%EC%9E%90%EB%A3%8C%EA%B5%AC%EC%A1%B0-%EC%B5%9C%EC%86%8C-%EC%8B%A0%EC%9E%A5-%ED%8A%B8%EB%A6%ACMST)

[최소신장트리](https://gmlwjd9405.github.io/2018/08/28/algorithm-mst.html)

[코드](https://brownbears.tistory.com/461)

