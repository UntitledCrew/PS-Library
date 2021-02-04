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