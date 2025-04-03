class Solution:
    def maximumTripletValue(self, nums: List[int]) -> int:
        # max_left = [0] * len(nums)
        # max_right = [0] * len(nums)

        # for i in range(1, len(nums)):
        #     max_left[i] = max(max_left[i - 1], nums[i - 1])
        
        # for i in range(len(nums) - 2, -1, -1):
        #     max_right[i] = max(max_right[i + 1], nums[i + 1])

        # out = 0
        # for i in range(len(nums)):
        #     triplet = (max_left[i] - nums[i]) * max_right[i]
        #     out = max(out, triplet)
        # return out
        out = 0
        i_max = 0
        s_max  = 0
        for idx, num in enumerate(nums):
            out = max(s_max * num, out)
            s_max = max(i_max - num, s_max)
            i_max = max(num, i_max)
        return out