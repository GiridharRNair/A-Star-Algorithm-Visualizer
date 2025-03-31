class Solution:
    def spiralOrder(self, matrix: List[List[int]]) -> List[int]:
        out = []
        left = 0
        right = len(matrix[0]) - 1
        top = 0
        down = len(matrix) - 1
        while left <= right and top <= down:
            # right
            for idx in range(left, right + 1):
                out.append(matrix[top][idx])
            top += 1

            if top > down:
                break

            # down
            for idx in range(top, down + 1):
                out.append(matrix[idx][right])
            right -= 1

            # left
            for idx in range(right, left - 1, -1):
                out.append(matrix[down][idx])
            down -= 1

            if left > right:
                break
            
            # up
            for idx in range(down, top - 1, -1):
                # print(matrix[idx][left])
                out.append(matrix[idx][left])
            left += 1
        return out
            

