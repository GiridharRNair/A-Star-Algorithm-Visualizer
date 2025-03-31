class Solution:
    def generateMatrix(self, n: int) -> List[List[int]]:
        out = [[0] * n for _ in range(n)]
        counter = 1
        left = 0
        right = n - 1
        up = 0
        down = n - 1
        while left <= right and up <= down:
            for idx in range(left, right + 1):
                out[up][idx] = counter
                counter += 1
            up += 1

            if left > right:
                break

            for idx in range(up, down + 1):
                out[idx][right] = counter
                counter += 1
            right -= 1

            for idx in range(right, left - 1, -1):
                out[down][idx] = counter
                counter += 1
            down -= 1

            if up > down:
                break

            for idx in range(down, up - 1, -1):
                out[idx][left] = counter
                counter += 1
            left += 1
        return out