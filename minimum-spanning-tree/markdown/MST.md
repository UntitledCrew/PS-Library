Spanning Tree ( 신장 트리 )

- 조건
  1. 모든 노드 포함
  2. 모든 노드가 연결
  3. 트리 속성 만족 ( 최소 연결 )
  4. 사이클 x

Minimun Spanning Tree ( 최소 신장 트리 )

- 연결된 무방향의 가중치 그래프
- 하나의 그래프에 여러개가 존재할 수 있음
- 쓰임
  - 통신망, 도로망, 유통망, 배관 등의 거리, 비용, 시간의 최소값

- 조건
  - 가능한 Spanning Tree 중에서 간선의 가중치 합이 최소인 Spanning Tree
  - ![mst1](\img\mst1.JPG)
- 주요 용어
  - 안전간선
    - 단절
      - 두 집합은 서로소
    - 교차
      - 서로소를 잇는 간선이 존재
    - 따름
      - 서로소 안의 노드들은 집합 내에서 연결되어있음
    - 경량
      - 가중치가 최소인 간선으로 연결

Kruskal
- 방법
  - 가중치를 기준으로 트리구조 (사이클이 생기지 않게) 생성
  - 모든 정점을 독립적인 집합으로 만듬
  - 모든 간선의 비용을 기준으로 정렬하고, 최소값의 양 노드를 비교
  - 두 노드의 최상위 노드를 확인하고, 서로 다른 집합일 경우 두 노드를 연결
  - Greedy Algo가 기초
  - Union Find ( & Disjoint set )
    - Disjoin set을 표현할 때 트리구조를 사용하면 Union Find
    - 노드 중 연결된 노드나 중복된 노드 찾을때 사용
    - Disjoint set
      - 서로 중복되지 않는 부분집합들로 나눠진 원소들에 대한 정보를 조작하고 저장
      - 서로소로 나눠진 부분집합 구조
      - 즉, Disjoint set = 서로소 집합 자료구조
    - 알고리즘
      - 초기화
        - 정점을 정렬
      - Union
        - 개별 집합을 하나의 집합으로 만듬, ( 두개의 트리를 하나로 )
      - Find
        - 사이클이 존재하는지 확인
        - 즉, 각 집합의 루트 노드를 확인
    - 고려사항
      - 최악의 경우 Linked List 로 O(N)을 가질 수 있음
    - 대처방안
      - Union by rank
        - 각 트리의 높이를 파악하고, 높이가 작은 트리를 큰 트리에 붙임
        - 즉, 큰 트리의 루트노드가 작은트리의 루트노드가 됨
        - 이를 통해 O(logN)으로 낮출 수 있음
      - Path compression
        - Find를 실행한 노드에서 거쳐간 노드를 루트에 바로 연결
        - 이를 통해 루트노드를 바로 알 수 있음
        - 
- 시간복잡도
  - 가중치 정렬시 O(N) or O(ElogE)
  - Find-set, Union 과정은 O(logN) 인데, 전체 간선만큼 반복하므로 O(ElogE)
  - 따라서 시간복잡도는 O(ElogE)

Prim
- 어떤 노드를 시작으로 하든 같은 트리가 그려짐
  
- 방법
  
  - 첫번째 노드부터 시작해서 순서대로 진행 (초기 셋팅시 모든 노드의 값은 inf)
  - 다음 노드를 우선적으로 확인하되, 가중치가 낮은 노드를 연결
  - 임의의 노드를 선택하고, '연결된 노드 집합' 에 넣음
  - 선택된 노드에 연결된 간선들을 '간선 리스트' 에 넣음
  - '간선 리스트' 중 최소 비용을 먼저 추출해서,
    - '연결된 노드 집합' 에 들어있으면 사이클이 발생할 수 있으므로 스킵
    - 없다면 '최소 신장 트리' 에 삽입
  - 추출한 간선은 리스트에서 제거
  - '간선 리스트' 가 빌 때 까지 반복
  
- 시간복잡도
  - heap 사용
  - 노드 초기화 O(|N|), Minimum Key를 뽑기위해 O(logN)
  - 현재 노드의 부모 노드를 설정하고, 가중치의 최소값을 업데이트하려면 최대 트리 높이까지 확인해야 하므로 O(logN)인데, 노드에 연결된 간선 수 만큼 반복하므로 O(E/N*logN)
  - 따라서 시간복잡도는 O((N+E)logN)
  
- 코드

  - ```javascript
    var Graph = (function() {
      function Vertex(key) {
        this.next = null;
        this.arc = null;
        this.key = key;
        this.inTree = null;
      }
      function Arc(data, dest, capacity) {
        this.nextArc = null;
        this.destination = dest;
        this.data = data;
        this.capacity = capacity;
        this.inTree = null;
      }
      function Graph() {
        this.count = 0;
        this.first = null;
      }
      Graph.prototype.insertVertex = function(key) {
        var vertex = new Vertex(key);
        var last = this.first;
        if (last) {
          while (last.next !== null) {
            last = last.next;
          }
          last.next = vertex;
        } else {
          this.first = vertex;
        }
        this.count++;
      };
      Graph.prototype.deleteVertex = function(key) {
        var vertex = this.first;
        var prev = null;
        while (vertex.key !== key) {
          prev = vertex;
          vertex = vertex.next;
        }
        if (!vertex) return false;
        if (!vertex.arc) return false;
        if (prev) {
          prev.next = vertex.next;
        } else {
          this.first = vertex.next;
        }
        this.count--;
      };
      Graph.prototype.insertArc = function(data, fromKey, toKey, capacity) {
        var from = this.first;
        var to = this.first;
        while (from && from.key !== fromKey) {
          from = from.next;
        }
        while (to && to.key !== toKey) {
          to = to.next;
        }
        if (!from || !to) return false;
        var arc = new Arc(data, to, capacity);
        var fromLast = from.arc;
        if (fromLast) {
          while (fromLast.nextArc != null) {
            fromLast = fromLast.nextArc;
          }
          fromLast.nextArc = arc;
        } else {
          from.arc = arc;
        }
      };
      Graph.prototype.deleteArc = function(fromKey, toKey) {
        var from = this.first;
        while (from !== null) {
          if (from.key === fromKey) break;
          from = from.next;
        }
        if (!from) return false;
        var fromArc = from.arc;
        var preArc;
        while (fromArc !== null) {
          if (toKey === fromArc.destination.key) break;
          preArc = fromArc;
          fromArc = fromArc.next;
        }
        if (!fromArc) return false;
        if (preArc) {
          preArc.nextArc = fromArc.nextArc;
        } else {
          from.arc = fromArc.nextArc;
        }
      };
      Graph.prototype.mst = function() {
        var first = this.first;
        var inTreeCount = 0;
        while (first) { // 모든 inTree를 false로 초기화
          first.inTree = false;
          var arc = first.arc;
          while (arc) {
            arc.inTree = false;
            arc = arc.nextArc;
          }
          first = first.next;
        }
        this.first.inTree = true; // 첫 버텍스를 MST에 넣습니다.
        inTreeCount++;
        console.log('%s 버텍스가 추가되었습니다.', this.first.key);
        var temp = this.first;
        var current;
        var minArc; // 최소 아크를 저장
        var minTemp; // 최소 아크의 출발 버텍스를 저장
        while (inTreeCount != this.count) { // 모든 버텍스를 추가할 때까지
          while (temp) {
          current = temp;
          temp = temp.next;
          if (!current.inTree) continue;
          arc = current.arc;
          while (arc) {
            if (!arc.destination.inTree) {
              if (!minArc) minArc = arc;
              if (minArc.data > arc.data) { // 기존 최솟값보다 더 작은 값을 찾았을 때 
                  minArc = arc; // 최소 아크를 찾음
                  minTemp = current; // 최소 아크의 출발 버텍스 저장
                }
              }
              arc = arc.nextArc;
            }
          }
          minArc.destination.inTree = true;
          minArc.inTree = true;
          inTreeCount++;
          console.log('%s 버텍스에서 %s 버텍스로 향하는 가중치 %d의 아크가 추가되었습니다.', minTemp.key, minArc.destination.key, minArc.data);
          minArc = null;
          temp = this.first;
        }
      };
      return Graph;
    })();
    ```

    - 위 코드는 배열을 사용했기에, O(N^2)
    - 하지만 이진 힙 사용시 O(ElogN), 피보나치 힙 사용시 O((E + N)logN) 가능

  - ```javascript
    var graph = new Graph();
    graph.insertVertex('A');
    graph.insertVertex('B');
    graph.insertVertex('C');
    graph.insertVertex('D');
    graph.insertVertex('E');
    graph.insertVertex('F');
    insertTwoWayArc(graph, 6, 'A', 'B');
    insertTwoWayArc(graph, 3, 'A', 'C');
    insertTwoWayArc(graph, 2, 'B', 'C');
    insertTwoWayArc(graph, 5, 'B', 'D');
    insertTwoWayArc(graph, 3, 'C', 'D');
    insertTwoWayArc(graph, 4, 'C', 'E');
    insertTwoWayArc(graph, 2, 'D', 'E');
    insertTwoWayArc(graph, 3, 'D', 'F');
    insertTwoWayArc(graph, 5, 'E', 'F');
    graph.mst();
    // A 버텍스가 추가되었습니다.
    // A 버텍스에서 C 버텍스로 향하는 가중치 3의 아크가 추가되었습니다.
    // C 버텍스에서 B 버텍스로 향하는 가중치 2의 아크가 추가되었습니다.
    // C 버텍스에서 D 버텍스로 향하는 가중치 3의 아크가 추가되었습니다.
    // D 버텍스에서 E 버텍스로 향하는 가중치 2의 아크가 추가되었습니다.
    // D 버텍스에서 F 버텍스로 향하는 가중치 3의 아크가 추가되었습니다.
    ```

    

- K vs P
  - 둘다 Greedy를 기초로 함 ( 당장 눈 앞의 최소 비용을 선택해서 결과적으로 최적의 솔루션)
  - K는 전체 간선 중 최소 비용에서 시작
  - P는 특정 노드에서 최소 비용으로 시작
  - K가 자료구조의 영향을 덜 받음
  - 그래프가 빽빽할 경우 P의 성능이 더 나음