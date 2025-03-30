class Solution:
    def partitionLabels(self, s: str) -> List[int]:
        last_index = {}
        for idx, c in enumerate(s):
            last_index[c] = idx

        out = []
        start = 0
        parition_end = 0
        for idx, c in enumerate(s):
            parition_end = max(
                parition_end, 
                last_index[c]
            )
            if idx == parition_end:
                out.append(parition_end - start + 1)
                start = idx + 1
        return out