redis.replicate_commands()

local current_time = tonumber(redis.call('TIME')[1])
local window_size = tonumber(ARGV[1])
local limit = tonumber(ARGV[2])
local window_start = current_time - (current_time % window_size)
local window_key = KEYS[1] .. ':' .. window_start
-- window_key 自增1
redis.call('INCR', window_key)
redis.call('EXPIRE', window_key, window_size)
local window_count = tonumber(redis.call('GET', window_key) or '0')
if window_count > limit then
    -- false
    return 1
else
    -- true
    return 0
end


