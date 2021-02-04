let fs = require('fs');
let input = fs.readFileSync('./dev/stdin').toString().split('\n')

function Prim() {
    const first = 1;
    node[1] = 0
    
    const checked = [1]
    let start = []
    for (let i = 1; i < V+1; i++) {
        if (node[i] > data[first][i]) {
            node[i] = data[first][i]
            start.push(data[first][i])
        }
    }

    while (start.length) {
        const next = start.shift()
        for (let i = 1; i < V+1; i++) {
            if (!checked.some(el => (el === data[next][i])) && node[next] > data[next][i]) {
                node[next] = data[next][i]
                checked.push(next)
            }
        }
    }
}

const V = input[0].split(' ').map(el => (+el))[0]
const E = input[0].split(' ').map(el => (+el))[1]

const node = new Array(V + 1).fill(Infinity)

const data = new Array(V + 1)
for (let i = 0; i < V + 1; i++) {
    data[i] = new Array(V + 1).fill(Infinity)
}

for (let i = 1; i < E+1; i++) {
    const from = input[i].split(' ').map(el => (+el))[0]
    const to = input[i].split(' ').map(el => (+el))[1]
    const weight =  input[i].split(' ').map(el => (+el))[2]
    data[from][to] = weight
    data[to][from] = weight
}

Prim()
console.log(node.slice(1,).reduce((curr, acc) => (curr+acc)))